package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.dto.CreatePlaylistRequest;
import com.ilnitsk.animusic.models.Anime;
import com.ilnitsk.animusic.models.Playlist;
import com.ilnitsk.animusic.repositories.AnimeRepository;
import com.ilnitsk.animusic.repositories.PlaylistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
        Anime anime = new Anime();
        anime.setId(1);
        anime.setTitle("Naruto");
        given(animeRepository.findById(1)).willReturn(Optional.of(anime));
        CreatePlaylistRequest request = new CreatePlaylistRequest(
                1,
                "Endings",
                "/"
        );
        given(playlistRepository.existsByNameAndAnimeId(request.getName(),1))
                .willReturn(false);
        underTest.createPlaylist(request);
        ArgumentCaptor<Playlist> playlistArgumentCaptor = ArgumentCaptor.forClass(Playlist.class);
        verify(playlistRepository).save(playlistArgumentCaptor.capture());
        Playlist playlist = playlistArgumentCaptor.getValue();
        assertThat(playlist.getAnime()).isEqualTo(anime);
        assertThat(playlist.getName()).isEqualTo(request.getName());
        assertThat(playlist.getImageUrl()).isEqualTo(request.getImageUrl());
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