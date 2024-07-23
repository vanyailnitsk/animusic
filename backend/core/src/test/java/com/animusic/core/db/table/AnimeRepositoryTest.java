package com.animusic.core.db.table;

import java.util.List;

import com.animusic.core.AnimusicApplication;
import com.animusic.core.conf.DatabaseConfig;
import com.animusic.core.db.model.Anime;
import com.animusic.core.db.model.AnimeBannerImage;
import com.animusic.core.db.model.Image;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig({
        AnimusicApplication.class,
        DatabaseConfig.class
})
//@Transactional
@Sql
@Slf4j
@DataJpaTest
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void findByIdExist() {
        var animeOptional = animeRepository.findById(1);
        assertThat(animeOptional).isNotEmpty();

        var anime = animeOptional.get();

        var expected = Anime.builder()
                .id(1)
                .title("Anime-1")
                .description("empty")
                .studio("MAPPA")
                .releaseYear(2003)
                .bannerImage(AnimeBannerImage.builder()
                        .id(1)
                        .image(Image.builder().id(1).source("s3:image-1").build())
                        .color("#ff0000")
                        .build())
                .cardImage(Image.builder().id(1).source("s3:image-1").build())
                .folderName("anime1")
                .build();
        assertThat(anime).isEqualTo(expected);
    }

    @Test
    void findByIdNotExists() {
        var animeOptional = animeRepository.findById(1234);
        assertThat(animeOptional).isEmpty();
    }

    @Test
    void saveGoodCase() {
        var anime = Anime.builder()
                .title("Naruto")
                .description("empty")
                .studio("MAPPA")
                .releaseYear(2003)
                .folderName("Naruto")
                .build();
        animeRepository.save(anime);
        assertThat(anime.getId()).isNotNull();
    }

    @Test
    void getAllAnime() {
        assertThat(animeRepository.findAll().size()).isEqualTo(4);
    }

    @Test
    void deleteById() {
        assertThat(animeRepository.findById(1)).isNotEmpty();
        animeRepository.deleteById(1);
        assertThat(animeRepository.findById(1)).isEmpty();
    }

    @Test
    void deleteAllById() {
        var ids = List.of(1, 2);
        assertThat(animeRepository.findById(1)).isNotEmpty();
        assertThat(animeRepository.findById(2)).isNotEmpty();

        animeRepository.deleteAllById(ids);
        assertThat(animeRepository.findById(1)).isEmpty();
        assertThat(animeRepository.findById(2)).isEmpty();
    }

    @Test
    void deleteAll() {
        assertThat(animeRepository.findAll().size()).isPositive();
        animeRepository.deleteAll();
        assertThat(animeRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    void findAnimeByTitle() {
        var expected = Anime.builder()
                .id(4)
                .title("Anime-4")
                .description("empty")
                .folderName("anime4")
                .releaseYear(2003)
                .studio("MAPPA")
                .build();

        var anime = animeRepository.findAnimeByTitle("Anime-4").get();
        assertThat(anime).isEqualTo(expected);
    }

    @Test
    void findAnimeByTitleNotExists() {
        var anime = animeRepository.findAnimeByTitle("not anime");
        assertThat(anime).isEmpty();
    }

    @Test
    void existsAnimeByTitle() {
        var exists = animeRepository.existsAnimeByTitle("Anime-4");
        assertThat(exists).isEqualTo(true);
    }
}