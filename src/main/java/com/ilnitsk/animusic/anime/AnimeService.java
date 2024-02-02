package com.ilnitsk.animusic.anime;

import com.ilnitsk.animusic.exception.AnimeNotFoundException;
import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.image.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AnimeService {
    private final AnimeRepository animeRepository;
    private final ImageService imageService;
    @Value("${images.directory}")
    private String imagesPath;

    @Autowired
    public AnimeService(AnimeRepository animeRepository, ImageService imageService) {
        this.animeRepository = animeRepository;
        this.imageService = imageService;
    }

    public Anime getAnimeInfo(Integer animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        anime.getSoundtracks()
                .forEach(s -> s.setAnimeName(anime.getTitle()));
        return anime;
    }



    public List<AnimeNavDTO> getAnimeDropdownList() {
        List<Anime> animeList = animeRepository.findAllByOrderByTitle();
        List<AnimeNavDTO> animeDropdownList = new ArrayList<>();
        for (Anime anime : animeList) {
            AnimeNavDTO dto = new AnimeNavDTO(anime.getId(), anime.getTitle());
            animeDropdownList.add(dto);
        }
        return animeDropdownList;
    }

    @Transactional
    public Anime createAnime(Anime anime, MultipartFile banner, MultipartFile card) {
        boolean existsTitle = animeRepository
                .existsAnimeByTitle(anime.getTitle());
        if (existsTitle) {
            throw new BadRequestException(
                    "Anime " + anime.getTitle() + " already exists"
            );
        }
        createBanner(anime,banner);
        createCard(anime,banner);
        return animeRepository.save(anime);
    }

    public ResponseEntity<byte[]> getBanner(Integer animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        String banner = anime.getBannerImagePath();
        if (banner == null || banner.isEmpty()) {
            return imageService.getDefaultBanner();
        }
        return imageService.getImage(anime.getFolderName(),banner);
    }

    public void createBanner(Anime anime,MultipartFile banner) {
        String extension = imageService.getImageExtension(banner.getOriginalFilename());
        String bannerFile = "banner%s".formatted(extension);
        imageService.saveImage(banner,anime.getFolderName(),"banner");
        anime.setBannerImagePath(bannerFile);
        animeRepository.save(anime);
    }

    @Transactional
    public void setBanner(Integer animeId, MultipartFile banner) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        createBanner(anime,banner);
    }

    public void createCard(Anime anime,MultipartFile card) {
        String extension = imageService.getImageExtension(card.getOriginalFilename());
        String cardFile = "card.%s".formatted(extension);
        imageService.saveImage(card,anime.getFolderName(),cardFile);
        anime.setBannerImagePath(cardFile);
    }

    @Transactional
    public void setCard(Integer animeId, MultipartFile card) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        createCard(anime,card);
    }


    public List<Anime> getAllAnime() {
        return animeRepository.findAll();
    }
    public void deleteAnime(Integer animeId) {
        animeRepository.deleteById(animeId);
    }


}
