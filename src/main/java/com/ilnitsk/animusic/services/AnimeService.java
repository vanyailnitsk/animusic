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
    private final SoundtrackService soundtrackService;

    @Autowired
    public AnimeService(AnimeRepository animeRepository, SoundtrackService soundtrackService) {
        this.animeRepository = animeRepository;
        this.soundtrackService = soundtrackService;
    }

    public List<Soundtrack> getSoundtracksByAnimeId(Integer animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new IllegalArgumentException("No anime with id " + animeId));
        List<Soundtrack> soundtracks = anime.getSoundtracks();
        soundtracks.forEach(s -> s.setAnimeName(anime.getTitle()));
        return soundtracks;
    }

    public Anime getAnimeInfo(Integer animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new IllegalArgumentException("No anime with id " + animeId));
        anime.getSoundtracks()
                .forEach(s -> s.setAnimeName(anime.getTitle()));
        anime.setSoundtracks(soundtrackService.getTypedSoundtrackList(anime.getSoundtracks()));
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
        return animeRepository.save(anime);
    }
}
