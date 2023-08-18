package com.ilnitsk.animusic.controllers;

import com.ilnitsk.animusic.dto.AnimeNavDTO;
import com.ilnitsk.animusic.models.Anime;
import com.ilnitsk.animusic.models.Soundtrack;
import com.ilnitsk.animusic.services.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
public class AnimeController {
    private final AnimeService animeService;

    @Autowired
    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping("/all-soundtracks-by-anime-id/{animeId}")
    public List<Soundtrack> getSoundtracksByAnimeId(@PathVariable Integer animeId) {
        return animeService.getSoundtracksByAnimeId(animeId);
    }

    @GetMapping("/info/{animeId}")
    public Anime getAnimeInfo(@PathVariable Integer animeId) {
        return animeService.getAnimeInfo(animeId);
    }

    @GetMapping("/navigation")
    public List<AnimeNavDTO> getAnimeDropdownList() {
        return animeService.getAnimeDropdownList();
    }

    @PostMapping("/create")
    public Anime createAnime(@RequestBody Anime anime) {
        return animeService.createAnime(anime);
    }
}
