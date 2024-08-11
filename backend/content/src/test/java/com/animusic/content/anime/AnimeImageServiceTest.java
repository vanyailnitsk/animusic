package com.animusic.content.anime;

import com.animusic.content.IntegrationTestBase;
import com.animusic.content.image.ImageService;
import com.animusic.core.db.model.Anime;
import com.animusic.core.db.model.AnimeBannerImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class AnimeImageServiceTest extends IntegrationTestBase {

    @Autowired
    AnimeImageService animeImageService;

    @Autowired
    ImageService imageService;

    @Test
    void setBanner() {
        var anime = Anime.builder()
                .title("Anime-1")
                .folderName("Anime1")
                .build();
        animeRepository.save(anime);
        var bannerFile = new MockMultipartFile("image", "logo.png", "img/png", "image".getBytes());
        var animeBanner = AnimeBannerImage.builder()
                .color("#00ff00")
                .build();
        var savedBanner = animeImageService.setBanner(anime.getId(), bannerFile, animeBanner);

        assertThat(savedBanner.getImage()).isNotNull();

        assertThat(savedBanner.getColor()).isEqualTo("#00ff00");
        assertThat(anime.getBannerImage()).isEqualTo(savedBanner);
    }

    @Test
    void setCard() {
        var anime = Anime.builder()
                .title("Anime-1")
                .folderName("Anime1")
                .build();
        animeRepository.save(anime);

        var cardFile = new MockMultipartFile("image", "logo.png", "img/png", "image".getBytes());
        var card = animeImageService.setCard(anime.getId(), cardFile);

        assertThat(card.getSource()).isNotNull();

        assertThat(anime.getCardImage()).isEqualTo(card);
    }
}