package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.dto.AnimeNavDTO;
import com.ilnitsk.animusic.models.Anime;
import com.ilnitsk.animusic.models.Soundtrack;
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

    public List<Soundtrack> getSoundtracksByAnimeId(Integer animeId) {
        return animeRepository.findById(animeId)
                .orElseThrow(() -> new IllegalArgumentException("No anime with id " + animeId)).getSoundtracks();
    }

    public Anime getAnimeInfo(Integer animeId) {
        return animeRepository.findById(animeId)
                .orElseThrow(() -> new IllegalArgumentException("No anime with id " + animeId));
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
        return animeRepository.save(anime);
    }
}
