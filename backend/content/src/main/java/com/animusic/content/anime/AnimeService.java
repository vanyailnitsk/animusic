package com.animusic.content.anime;

import java.util.List;
import java.util.Optional;

import com.animusic.core.db.model.Anime;
import com.animusic.core.db.table.AnimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public Optional<Anime> getAnime(Integer animeId) {
        return animeRepository.findById(animeId);
    }

    public List<Anime> getAllAnime() {
        log.info("Requested all anime list");
        return animeRepository.findAllOrderByTitle();
    }

    @Transactional
    public Anime createAnime(Anime anime) {
        boolean existsTitle = animeRepository
                .existsAnimeByTitle(anime.getTitle());
        if (existsTitle) {
            throw new AnimeAlreadyExistsException(anime.getTitle());
        }
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
