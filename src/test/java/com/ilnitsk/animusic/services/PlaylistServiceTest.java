package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.repositories.PlaylistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {
    @Mock
    private PlaylistRepository playlistRepository;
    private PlaylistService underTest;
    @BeforeEach
    void setUp() {
        underTest = new PlaylistService(playlistRepository);
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