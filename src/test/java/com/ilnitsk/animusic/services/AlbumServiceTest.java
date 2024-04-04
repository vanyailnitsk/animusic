package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.album.dao.Album;
import com.ilnitsk.animusic.album.dto.CreateAlbumDto;
import com.ilnitsk.animusic.album.repository.AlbumRepository;
import com.ilnitsk.animusic.album.service.AlbumService;
import com.ilnitsk.animusic.anime.dao.Anime;
import com.ilnitsk.animusic.anime.repository.AnimeRepository;
import com.ilnitsk.animusic.exception.AnimeNotFoundException;
import com.ilnitsk.animusic.exception.BadRequestException;
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
    private AlbumRepository albumRepository;
    @Mock
    private AnimeRepository animeRepository;
    private AlbumService underTest;
    @BeforeEach
    void setUp() {
        underTest = new AlbumService(albumRepository, animeRepository, null);
    }

    @Test
    void createAlbum() {
        Anime anime = new Anime();
        anime.setId(1);
        anime.setTitle("Naruto");
        given(animeRepository.findById(1)).willReturn(Optional.of(anime));
        CreateAlbumDto request = new CreateAlbumDto(
                1,
                "Endings"
        );
        given(albumRepository.existsByNameAndAnimeId(request.getName(),1))
                .willReturn(false);
        underTest.createAlbum(request);
        ArgumentCaptor<Album> playlistArgumentCaptor = ArgumentCaptor.forClass(Album.class);
        verify(albumRepository).save(playlistArgumentCaptor.capture());
        Album album = playlistArgumentCaptor.getValue();
        assertThat(album.getAnime()).isEqualTo(anime);
        assertThat(album.getName()).isEqualTo(request.getName());
    }

    @Test
    public void testCreateAlbumWithNonExistingAnime() {
        CreateAlbumDto request = new CreateAlbumDto(1, "Test Album");

        when(animeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(AnimeNotFoundException.class, () -> {
            underTest.createAlbum(request);
        });
    }

    @Test
    public void testCreateAlbumWithDuplicateName() {
        CreateAlbumDto request = new CreateAlbumDto(1, "Test Album");

        Anime anime = new Anime();
        anime.setId(1);
        when(animeRepository.findById(1)).thenReturn(Optional.of(anime));

        when(albumRepository.existsByNameAndAnimeId("Test Album", 1)).thenReturn(true);

        assertThatThrownBy(() -> underTest.createAlbum(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(
                        "Album " + request.getName() + " in anime " +anime.getTitle()+" already exists"
                );
    }


    @Test
    void getAlbumsByAnimeId() {
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
        given(albumRepository.getAlbumsByAnimeId(1)).willReturn(
                Optional.of(List.of(album1, album2)));
        List<Album> albums = underTest.getAlbumsByAnimeId(1);
        verify(albumRepository).getAlbumsByAnimeId(1);
        assertThat(albums).isNotEmpty();
        assertThat(albums).isEqualTo(List.of(album1, album2));
    }

    @Test
    void getAlbumsByAnimeIdWithNonExistingAnime() {
        given(albumRepository.getAlbumsByAnimeId(1)).willReturn(
                Optional.empty());
        assertThatThrownBy(() -> underTest.getAlbumsByAnimeId(1))
                .isInstanceOf(AnimeNotFoundException.class)
                .hasMessageContaining("Anime with id 1 not found");
        verify(albumRepository).getAlbumsByAnimeId(1);
    }

    @Test
    void getAlbumById() {
        Anime anime = new Anime("Naruto","mock", Year.of(2002),"","");
        Album album = Album.builder()
                .id(1)
                .name("mock")
                .anime(anime)
                .soundtracks(new ArrayList<>())
                .build();
        given(albumRepository.findById(1)).willReturn(Optional.of(album));
        Album provided = underTest.getAlbumById(1);
        verify(albumRepository).findById(1);
        assertThat(provided).isEqualTo(album);
    }

    @Test
    void deleteAlbum() {
        Album album1 = Album.builder()
                .id(1)
                .name("mock")
                .soundtracks(new ArrayList<>())
                .build();
        underTest.deleteAlbum(1);
        verify(albumRepository).deleteById(1);
    }
}