package com.animusic.content.anime;

import com.animusic.content.image.ImageService;
import com.animusic.core.db.model.AnimeBannerImage;
import com.animusic.core.db.model.Image;
import com.animusic.core.db.table.AnimeBannerImageRepository;
import com.animusic.core.db.table.AnimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnimeImageService {

    private final AnimeRepository animeRepository;

    private final ImageService imageService;

    private final AnimeBannerImageRepository animeBannerImageRepository;

    @Transactional
    public AnimeBannerImage setBanner(Integer animeId, MultipartFile banner, AnimeBannerImage bannerImage) {
        var anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        var image = imageService.createImageInAnimeDirectory(
                anime.getFolderName(),
                "banner",
                banner
        );
        bannerImage.setImage(image);
        anime.setBannerImage(bannerImage);
        return animeBannerImageRepository.save(bannerImage);
    }

    @Transactional
    public Image setCard(Integer animeId, MultipartFile card) {
        var anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        var cardImage = imageService.createImageInAnimeDirectory(
                anime.getFolderName(),
                "card",
                card
        );
        anime.setCardImage(cardImage);
        return cardImage;
    }
}
