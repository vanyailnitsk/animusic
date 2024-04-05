package com.ilnitsk.animusic.anime.service;

import com.ilnitsk.animusic.anime.dao.Anime;
import com.ilnitsk.animusic.anime.dto.UpdateAnimeDto;
import com.ilnitsk.animusic.anime.repository.AnimeRepository;
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

import java.time.Year;
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
