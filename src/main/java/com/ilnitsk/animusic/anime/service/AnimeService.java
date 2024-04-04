package com.ilnitsk.animusic.anime.service;

import com.ilnitsk.animusic.anime.dao.Anime;
import com.ilnitsk.animusic.anime.dto.UpdateAnimeDto;
import com.ilnitsk.animusic.anime.repository.AnimeRepository;
import com.ilnitsk.animusic.exception.AnimeNotFoundException;
import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.image.service.ImageService;
import com.ilnitsk.animusic.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Year;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;
    private final ImageService imageService;
    private final S3Service s3Service;


    public Anime getAnimeInfo(Integer animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        return anime;
    }

    public List<Anime> getAllAnime() {
        return animeRepository.findAllByOrderByTitle();
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
        animeRepository.save(anime);
        String bannerPath = createBanner(anime,banner);
        String cardPath = createCard(anime,card);
        anime.setBannerImagePath(bannerPath);
        anime.setCardImagePath(cardPath);
        return animeRepository.save(anime);
    }
    public void deleteAnime(Integer animeId) {
        animeRepository.deleteById(animeId);
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

    public String createBanner(Anime anime,MultipartFile banner) {
        String bannerName = "%s/images/%s".formatted(anime.getFolderName(),"banner");
        return s3Service.createBlob(bannerName,banner);
    }

    @Transactional
    public void setBanner(Integer animeId, MultipartFile banner) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        String bannerPath = createBanner(anime,banner);
        anime.setBannerImagePath(bannerPath);
    }

    public ResponseEntity<byte[]> getCard(Integer animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        String card = anime.getCardImagePath();
        if (card == null || card.isEmpty()) {
            return imageService.getDefaultCard();
        }
        return imageService.getImage(anime.getFolderName(),card);
    }

    public String createCard(Anime anime,MultipartFile card) {
        String cardName = "%s/images/%s".formatted(anime.getFolderName(),"card");
        return s3Service.createBlob(cardName,card);
    }

    @Transactional
    public void setCard(Integer animeId, MultipartFile card) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        String cardPath = createCard(anime,card);
        anime.setCardImagePath(cardPath);
    }

    @Transactional
    public Anime updateAnime(UpdateAnimeDto updateAnimeDto, Integer animeId) {
        return animeRepository.findById(animeId).map(
                anime -> {
                    anime.setTitle(updateAnimeDto.getTitle());
                    anime.setStudio(updateAnimeDto.getStudio());
                    anime.setReleaseYear(Year.of(updateAnimeDto.getReleaseYear()));
                    anime.setDescription(updateAnimeDto.getDescription());
                    anime.setFolderName(updateAnimeDto.getFolderName());
                    return anime;
                }
        ).orElseThrow(() -> new AnimeNotFoundException(animeId));
    }
}
