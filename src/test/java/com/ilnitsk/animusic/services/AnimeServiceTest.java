package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.anime.*;
import com.ilnitsk.animusic.exception.AnimeNotFoundException;
import com.ilnitsk.animusic.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.List;
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
        underTest = new AnimeService(animeRepository, new ImageService());
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
    void canGetAnimeDropdownList() {
        Anime anime1 = Anime.builder().id(1).title("Anime 1").build();
        Anime anime2 = Anime.builder().id(2).title("Anime 2").build();
        given(animeRepository.findAllByOrderByTitle()).willReturn(List.of(anime1,anime2));
        List<AnimeNavDTO> result = underTest.getAnimeDropdownList();
        assertThat(result).isNotEmpty();
        assertThat(result).contains(
                new AnimeNavDTO(1, "Anime 1"), new AnimeNavDTO(2, "Anime 2"));
        verify(animeRepository).findAllByOrderByTitle();
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