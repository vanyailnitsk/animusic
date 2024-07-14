package com.animusic.anime.service;

import com.animusic.anime.AnimeAlreadyExistsException;
import com.animusic.anime.AnimeNotFoundException;
import com.animusic.anime.dao.Anime;
import com.animusic.anime.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;
    private final AnimeImageService animeImageService;


    public Anime getAnimeInfo(Integer animeId) {
        return animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
    }

    public List<Anime> getAllAnime() {
        log.info("Requested all anime list");
        return animeRepository.findAllByOrderByTitle();
    }

    @Transactional
    public Anime createAnime(Anime anime) {
        boolean existsTitle = animeRepository
                .existsAnimeByTitle(anime.getTitle());
        if (existsTitle) {
            throw new AnimeAlreadyExistsException(anime.getTitle());
        }
        animeRepository.save(anime);
        return animeRepository.save(anime);
    }
    public void deleteAnime(Integer animeId) {
        animeRepository.deleteById(animeId);
    }



    @Transactional
    public Anime updateAnime(Anime updateAnime, Integer animeId) {
        return animeRepository.findById(animeId).map(
                anime -> {
                    anime.setTitle(updateAnime.getTitle());
                    anime.setStudio(updateAnime.getStudio());
                    anime.setReleaseYear(updateAnime.getReleaseYear());
                    anime.setDescription(updateAnime.getDescription());
                    anime.setFolderName(updateAnime.getFolderName());
                    return anime;
                }
        ).orElseThrow(() -> new AnimeNotFoundException(animeId));
    }
}
