package com.ilnitsk.animusic.repositories;

import com.ilnitsk.animusic.anime.dao.Anime;
import com.ilnitsk.animusic.anime.repository.AnimeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository underTest;

    @Test
    void existsAnimeByTitle() {
        String title = "Naruto";
        Anime anime = new Anime(title,"mock", Year.of(2002),"","");
        underTest.save(anime);
        boolean provided = underTest.existsAnimeByTitle(title);
        assertThat(provided).isTrue();
    }
    @Test
    void findAnimeByTitle() {
        String title = "Naruto";
        Anime anime = new Anime(title,"mock", Year.of(2002),"","");
        underTest.save(anime);
        Anime provided = underTest.findAnimeByTitle(title);
        assertEquals(anime,provided);
    }
    @Test
    void notFindAnimeByTitle() {
        String title = "Naruto";
        Anime anime = new Anime(title,"mock", Year.of(2002),"","");
        Anime provided = underTest.findAnimeByTitle(title);
        assertNotEquals(anime,provided);
    }
}