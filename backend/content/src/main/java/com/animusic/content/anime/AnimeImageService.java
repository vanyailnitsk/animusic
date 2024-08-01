package com.animusic.content.anime;

import com.animusic.content.image.ImageService;
import com.animusic.core.db.model.Anime;
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
    private final AnimeBannerImageRepository bannerImageRepository;

    @Transactional
    public AnimeBannerImage setBanner(Integer animeId, MultipartFile banner, AnimeBannerImage bannerImage) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        Image image = imageService.createAnimeImage(anime.getFolderName(), "banner", banner);
        bannerImage.setImage(image);
        bannerImage = bannerImageRepository.save(bannerImage);
        anime.setBannerImage(bannerImage);
        return bannerImage;
    }

    @Transactional
    public Image setCard(Integer animeId, MultipartFile card) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        Image cardImage = imageService.createAnimeImage(anime.getFolderName(), "card", card);
        anime.setCardImage(cardImage);
        return cardImage;
    }
}
