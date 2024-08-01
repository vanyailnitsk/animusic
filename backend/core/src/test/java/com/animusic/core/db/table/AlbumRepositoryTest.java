package com.animusic.core.db.table;

import com.animusic.core.db.DatabaseTest;
import com.animusic.core.db.model.Album;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Sql
class AlbumRepositoryTest extends DatabaseTest {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    AnimeRepository animeRepository;

    @Test
    void existsByNameAndAnimeId() {
        var album = Album.builder()
                .anime(animeRepository.findById(1).get())
                .name("Openings")
                .build();
        albumRepository.save(album);

        assertThat(albumRepository.existsByNameAndAnimeId("Openings", 1)).isTrue();
        assertThat(albumRepository.existsByNameAndAnimeId("Endings", 1)).isFalse();
    }
}