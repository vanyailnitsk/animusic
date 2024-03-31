package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.anime.Anime;
import com.ilnitsk.animusic.anime.AnimeRepository;
import com.ilnitsk.animusic.exception.AnimeNotFoundException;
import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.playlist.Album;
import com.ilnitsk.animusic.playlist.AlbumService;
import com.ilnitsk.animusic.playlist.PlaylistRepository;
import com.ilnitsk.animusic.playlist.dto.CreatePlaylistRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {
    @Mock
    private PlaylistRepository playlistRepository;
    @Mock
    private AnimeRepository animeRepository;
    private AlbumService underTest;
    @BeforeEach
    void setUp() {
        underTest = new AlbumService(playlistRepository, animeRepository, null);
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
        ArgumentCaptor<Album> playlistArgumentCaptor = ArgumentCaptor.forClass(Album.class);
        verify(playlistRepository).save(playlistArgumentCaptor.capture());
        Album album = playlistArgumentCaptor.getValue();
        assertThat(album.getAnime()).isEqualTo(anime);
        assertThat(album.getName()).isEqualTo(request.getName());
        assertThat(album.getImageUrl()).isEqualTo(request.getImageUrl());
    }

    @Test
    public void testCreatePlaylistWithNonExistingAnime() {
        CreatePlaylistRequest request = new CreatePlaylistRequest(1, "Test Playlist", "/test-image.jpg");

        when(animeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(AnimeNotFoundException.class, () -> {
            underTest.createPlaylist(request);
        });
    }

    @Test
    public void testCreatePlaylistWithDuplicateName() {
        CreatePlaylistRequest request = new CreatePlaylistRequest(1, "Test Playlist", "/test-image.jpg");

        Anime anime = new Anime();
        anime.setId(1);
        when(animeRepository.findById(1)).thenReturn(Optional.of(anime));

        when(playlistRepository.existsByNameAndAnimeId("Test Playlist", 1)).thenReturn(true);

        assertThatThrownBy(() -> underTest.createPlaylist(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(
                        "Playlist " + request.getName() + " in anime " +anime.getTitle()+" already exists"
                );
    }


    @Test
    void getPlaylistsByAnimeId() {
        Anime anime = new Anime();
        anime.setId(1);
        Album album1 = Album.builder()
                .id(1)
                .name("mock")
                .anime(anime)
                .soundtracks(new ArrayList<>())
                .build();
        Album album2 = Album.builder()
                .id(2)
                .name("mock1")
                .anime(anime)
                .soundtracks(new ArrayList<>())
                .build();
        given(playlistRepository.getPlaylistsByAnimeId(1)).willReturn(
                Optional.of(List.of(album1, album2)));
        List<Album> albums = underTest.getPlaylistsByAnimeId(1);
        verify(playlistRepository).getPlaylistsByAnimeId(1);
        assertThat(albums).isNotEmpty();
        assertThat(albums).isEqualTo(List.of(album1, album2));
    }

    @Test
    void getPlaylistsByAnimeIdWithNonExistingAnime() {
        given(playlistRepository.getPlaylistsByAnimeId(1)).willReturn(
                Optional.empty());
        assertThatThrownBy(() -> underTest.getPlaylistsByAnimeId(1))
                .isInstanceOf(AnimeNotFoundException.class)
                .hasMessageContaining("Anime with id 1 not found");
        verify(playlistRepository).getPlaylistsByAnimeId(1);
    }

    @Test
    void getPlaylistById() {
        Anime anime = new Anime("Naruto","mock", Year.of(2002),"","");
        Album album = Album.builder()
                .id(1)
                .name("mock")
                .anime(anime)
                .soundtracks(new ArrayList<>())
                .build();
        given(playlistRepository.findById(1)).willReturn(Optional.of(album));
        Album provided = underTest.getPlaylistById(1);
        verify(playlistRepository).findById(1);
        assertThat(provided).isEqualTo(album);
    }

    @Test
    void deletePlaylist() {
        Album album1 = Album.builder()
                .id(1)
                .name("mock")
                .soundtracks(new ArrayList<>())
                .build();
        underTest.deletePlaylist(1);
        verify(playlistRepository).deleteById(1);
    }
}