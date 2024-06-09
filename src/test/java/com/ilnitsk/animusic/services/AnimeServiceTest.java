package com.ilnitsk.animusic.services;

import com.animusic.anime.AnimeNotFoundException;
import com.ilnitsk.animusic.anime.dao.Anime;
import com.ilnitsk.animusic.anime.repository.AnimeRepository;
import com.ilnitsk.animusic.anime.service.AnimeService;
import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.image.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
        underTest = new AnimeService(animeRepository, new ImageService(null,null),null,null);
    }

    @Test
    void canGetAnimeInfo() {
        Anime anime = new Anime("Naruto", "mock", "", "");
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
        Anime anime = new Anime("Naruto", "mock",  "", "");
        given(animeRepository.existsAnimeByTitle(anime.getTitle()))
                .willReturn(true);
        assertThatThrownBy(() -> underTest.createAnime(anime))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Anime " + anime.getTitle() + " already exists");
        verify(animeRepository, never()).save(any());
    }
}