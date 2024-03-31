package com.ilnitsk.animusic.repositories;

import com.ilnitsk.animusic.anime.Anime;
import com.ilnitsk.animusic.anime.AnimeRepository;
import com.ilnitsk.animusic.playlist.Album;
import com.ilnitsk.animusic.playlist.PlaylistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AlbumRepositoryTest {
    private PlaylistRepository underTest;
    private AnimeRepository animeRepository;

    @Autowired
    public AlbumRepositoryTest(PlaylistRepository underTest, AnimeRepository animeRepository) {
        this.underTest = underTest;
        this.animeRepository = animeRepository;
    }

    @Test
    void existsByNameAndAnimeId() {
        String title = "Naruto";
        Anime anime = new Anime(title,"mock", Year.of(2002),"","");
        animeRepository.save(anime);
        Album album = Album.builder()
                .name("Openings")
                .anime(anime)
                .build();
        underTest.save(album);
        boolean provided = underTest.existsByNameAndAnimeId(album.getName(),anime.getId());
        assertThat(provided).isTrue();
    }
    @Test
    void notExistsByNameAndAnimeIdOfName() {
        String title = "Naruto";
        Anime anime = new Anime(title,"mock", Year.of(2002),"","");
        animeRepository.save(anime);
        Album album = Album.builder()
                .name("Openings")
                .anime(anime)
                .build();
        underTest.save(album);
        boolean provided = underTest.existsByNameAndAnimeId("Not existed",anime.getId());
        assertThat(provided).isFalse();
    }
    @Test
    void notExistsByNameAndAnimeIdOfAnimeId() {
        String title = "Naruto";
        Anime anime = new Anime(title,"mock", Year.of(2002),"","");
        animeRepository.save(anime);
        Album album = Album.builder()
                .name("Openings")
                .anime(anime)
                .build();
        underTest.save(album);
        boolean provided = underTest.existsByNameAndAnimeId(album.getName(),100500);
        assertThat(provided).isFalse();
    }
}