package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.repositories.AnimeRepository;
import com.ilnitsk.animusic.repositories.PlaylistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {
    @Mock
    private PlaylistRepository playlistRepository;
    @Mock
    private AnimeRepository animeRepository;
    private PlaylistService underTest;
    @BeforeEach
    void setUp() {
        underTest = new PlaylistService(playlistRepository, animeRepository);
    }

    @Test
    void createPlaylist() {

    }

    @Test
    void getPlaylistsByAnimeId() {
    }

    @Test
    void getPlaylistsById() {
    }

    @Test
    void deletePlaylist() {
    }
}