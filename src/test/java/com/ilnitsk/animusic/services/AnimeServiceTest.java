package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.anime.Anime;
import com.ilnitsk.animusic.anime.AnimeRepository;
import com.ilnitsk.animusic.anime.AnimeService;
import com.ilnitsk.animusic.exception.AnimeNotFoundException;
import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.image.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {
    @Mock
    private AnimeRepository animeRepository;
    private AnimeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AnimeService(animeRepository, new ImageService(null));
    }

    @Test
    void canGetAnimeInfo() {
        Anime anime = new Anime("Naruto", "mock", Year.of(2002), "", "");
        anime.setId(1);
        given(animeRepository.findById(anime.getId()))
                .willReturn(Optional.of(anime));
        Anime actual = underTest.getAnimeInfo(anime.getId());
        verify(animeRepository).findById(anime.getId());
        assertThat(actual).isEqualTo(anime);
    }

    @Test
    void willThrowWhenNoAnimeFound() {
        assertThatThrownBy(() -> underTest.getAnimeInfo(1))
                .isInstanceOf(AnimeNotFoundException.class)
                .hasMessageContaining("Anime with id 1 not found");
        verify(animeRepository).findById(1);
    }

    @Test
    void willThrowWhenTitleTaken() {
        Anime anime = new Anime("Naruto", "mock", Year.of(2002), "", "");
        given(animeRepository.existsAnimeByTitle(anime.getTitle()))
                .willReturn(true);
        assertThatThrownBy(() -> underTest.createAnime(anime,null,null))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Anime " + anime.getTitle() + " already exists");
        verify(animeRepository, never()).save(any());
    }
}