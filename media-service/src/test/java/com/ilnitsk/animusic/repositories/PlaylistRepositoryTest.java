package com.ilnitsk.animusic.repositories;

import com.ilnitsk.animusic.anime.Anime;
import com.ilnitsk.animusic.anime.AnimeRepository;
import com.ilnitsk.animusic.playlist.Playlist;
import com.ilnitsk.animusic.playlist.PlaylistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PlaylistRepositoryTest {
    private PlaylistRepository underTest;
    private AnimeRepository animeRepository;

    @Autowired
    public PlaylistRepositoryTest(PlaylistRepository underTest,AnimeRepository animeRepository) {
        this.underTest = underTest;
        this.animeRepository = animeRepository;
    }

    @Test
    void existsByNameAndAnimeId() {
        String title = "Naruto";
        Anime anime = new Anime(title,"mock", Year.of(2002),"","");
        animeRepository.save(anime);
        Playlist playlist = Playlist.builder()
                .name("Openings")
                .anime(anime)
                .build();
        underTest.save(playlist);
        boolean provided = underTest.existsByNameAndAnimeId(playlist.getName(),anime.getId());
        assertThat(provided).isTrue();
    }
    @Test
    void notExistsByNameAndAnimeIdOfName() {
        String title = "Naruto";
        Anime anime = new Anime(title,"mock", Year.of(2002),"","");
        animeRepository.save(anime);
        Playlist playlist = Playlist.builder()
                .name("Openings")
                .anime(anime)
                .build();
        underTest.save(playlist);
        boolean provided = underTest.existsByNameAndAnimeId("Not existed",anime.getId());
        assertThat(provided).isFalse();
    }
    @Test
    void notExistsByNameAndAnimeIdOfAnimeId() {
        String title = "Naruto";
        Anime anime = new Anime(title,"mock", Year.of(2002),"","");
        animeRepository.save(anime);
        Playlist playlist = Playlist.builder()
                .name("Openings")
                .anime(anime)
                .build();
        underTest.save(playlist);
        boolean provided = underTest.existsByNameAndAnimeId(playlist.getName(),100500);
        assertThat(provided).isFalse();
    }
}