package com.animusic.anime.service;

import com.animusic.anime.dao.Anime;
import com.animusic.anime.dto.UpdateAnimeDto;
import com.animusic.anime.repository.AnimeRepository;
import com.ilnitsk.animusic.album.dao.Album;
import com.ilnitsk.animusic.exception.AnimeNotFoundException;
import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.image.dao.AnimeBannerImage;
import com.ilnitsk.animusic.image.dao.Image;
import com.ilnitsk.animusic.image.service.AnimeBannerImageService;
import com.ilnitsk.animusic.image.service.ImageService;
import com.ilnitsk.animusic.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;
    private final ImageService imageService;
    private final S3Service s3Service;
    private final AnimeBannerImageService bannerImageService;


    public Anime getAnimeInfo(Integer animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        List<Album> albums = anime.getAlbums();
        if (albums!= null) {
            albums.sort((a1,a2) -> {
                List<String> categoryOrder = List.of("Openings", "Endings", "Themes", "Scene songs");

                int index1 = categoryOrder.indexOf(a1.getName());
                int index2 = categoryOrder.indexOf(a2.getName());

                return Integer.compare(index1, index2);
            });
        }
        return anime;
    }

    public List<Anime> getAllAnime() {
        log.info("Requested all anime list");
        return animeRepository.findAllByOrderByTitle();
    }

    @Transactional
    public Anime createAnime(Anime anime) {
        boolean existsTitle = animeRepository
                .existsAnimeByTitle(anime.getTitle());
        if (existsTitle) {
            throw new BadRequestException(
                    "Anime " + anime.getTitle() + " already exists"
            );
        }
        System.out.println(anime.getReleaseYear());
        animeRepository.save(anime);
        return animeRepository.save(anime);
    }
    public void deleteAnime(Integer animeId) {
        animeRepository.deleteById(animeId);
    }

    @Transactional
    public AnimeBannerImage setBanner(Integer animeId, MultipartFile banner, AnimeBannerImage bannerImage) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        AnimeBannerImage bannerCreated = bannerImageService.createAnimeBannerImage(anime,banner,bannerImage);
        anime.setBannerImage(bannerCreated);
        return bannerCreated;
    }

    @Transactional
    public Image setCard(Integer animeId, MultipartFile card) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        Image cardImage = imageService.createAnimeImage(anime.getFolderName(),"card",card);
        anime.setCardImage(cardImage);
        return cardImage;
    }

    @Transactional
    public Anime updateAnime(UpdateAnimeDto updateAnimeDto, Integer animeId) {
        return animeRepository.findById(animeId).map(
                anime -> {
                    anime.setTitle(updateAnimeDto.title());
                    anime.setStudio(updateAnimeDto.studio());
                    anime.setReleaseYear(updateAnimeDto.releaseYear());
                    anime.setDescription(updateAnimeDto.description());
                    anime.setFolderName(updateAnimeDto.folderName());
                    return anime;
                }
        ).orElseThrow(() -> new AnimeNotFoundException(animeId));
    }
}
