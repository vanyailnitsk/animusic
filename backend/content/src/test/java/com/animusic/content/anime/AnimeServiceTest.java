package com.animusic.content.anime;

import com.animusic.content.IntegrationTestBase;
import com.animusic.core.db.model.Anime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class AnimeServiceTest extends IntegrationTestBase {

    @Autowired
    AnimeService animeService;

    @Test
    void createAnime() {
        var anime = Anime.builder()
                .title("Naruto")
                .folderName("Naruto")
                .releaseYear(2000)
                .description("description")
                .build();
        animeService.createAnime(anime);

        assertThat(animeRepository.findById(anime.getId()).get()).isEqualTo(anime);
    }

    @Test
    void createAnimeAlreadyExists() {
        var anime = Anime.builder()
                .title("Naruto")
                .folderName("Naruto")
                .releaseYear(2000)
                .description("description")
                .build();
        animeRepository.save(anime);

        var duplicate = Anime.builder()
                .title("Naruto")
                .folderName("Naruto")
                .releaseYear(2000)
                .description("description")
                .build();

        assertThatThrownBy(() -> animeService.createAnime(duplicate))
                .isInstanceOf(AnimeAlreadyExistsException.class);
    }

    @Test
    void updateAnime() {
    }
}