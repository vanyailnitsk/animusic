package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.dto.AnimeNavDTO;
import com.ilnitsk.animusic.exception.AnimeNotFoundException;
import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.models.Anime;
import com.ilnitsk.animusic.repositories.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimeService {
    private final AnimeRepository animeRepository;

    @Autowired
    public AnimeService(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    public Anime getAnimeInfo(Integer animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        anime.getSoundtracks()
                .forEach(s -> s.setAnimeName(anime.getTitle()));
        return anime;
    }

    public List<AnimeNavDTO> getAnimeDropdownList() {
        List<Anime> animeList = animeRepository.findAll();
        List<AnimeNavDTO> animeDropdownList = new ArrayList<>();
        for (Anime anime : animeList) {
            AnimeNavDTO dto = new AnimeNavDTO(anime.getId(), anime.getTitle());
            animeDropdownList.add(dto);
        }

        return animeDropdownList;
    }

    public Anime createAnime(Anime anime) {
        boolean existsTitle = animeRepository
                .existsAnimeByTitle(anime.getTitle());
        if (existsTitle) {
            throw new BadRequestException(
                    "Anime " + anime.getTitle() + " already exists"
            );
        }
        return animeRepository.save(anime);
    }
}
