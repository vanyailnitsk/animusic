package com.animusic.core.db.table;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.animusic.core.AnimusicApplication;
import com.animusic.core.conf.DatabaseConfig;
import com.animusic.core.db.model.Anime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig({
        AnimusicApplication.class,
        DatabaseConfig.class
})
@Transactional
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    void findAnimeByTitle() {
        var anime = Anime.builder()
                .title("Anime-1")
                .folderName("anime1")
                .studio("MAPPA")
                .releaseYear(2003)
                .description("")
                .build();
        animeRepository.save(anime);

        assertThat(animeRepository.findAnimeByTitle("Anime-1")).isNotEmpty();
        assertThat(animeRepository.findAnimeByTitle("Anime-2")).isEmpty();

    }

    @Test
    void existsAnimeByTitle() {
        var anime = Anime.builder()
                .title("Anime-1")
                .folderName("anime1")
                .studio("MAPPA")
                .releaseYear(2003)
                .description("")
                .build();
        animeRepository.save(anime);

        assertThat(animeRepository.existsAnimeByTitle("Anime-1")).isTrue();
        assertThat(animeRepository.existsAnimeByTitle("Anime-2")).isFalse();
    }

    @Test
    void findAllOrderByTitle() {
        var animeList = List.of(
                Anime.builder()
                        .title("Naruto")
                        .folderName("anime1")
                        .studio("MAPPA")
                        .releaseYear(2003)
                        .description("")
                        .build(),
                Anime.builder()
                        .title("AOT")
                        .folderName("anime2")
                        .studio("MAPPA")
                        .releaseYear(2003)
                        .description("")
                        .build(),
                Anime.builder()
                        .title("HunterXHunter")
                        .folderName("anime3")
                        .studio("MAPPA")
                        .releaseYear(2003)
                        .description("")
                        .build()
        );

        animeRepository.saveAll(animeList);

        var expected = new ArrayList<>(animeList);
        expected.sort(Comparator.comparing(Anime::getTitle));

        assertThat(animeRepository.findAllOrderByTitle()).isEqualTo(expected);
    }
}