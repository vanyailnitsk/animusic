package com.ilnitsk.animusic.controllers;

import com.ilnitsk.animusic.dto.AnimeNavDTO;
import com.ilnitsk.animusic.dto.SoundtrackRequest;
import com.ilnitsk.animusic.models.Anime;
import com.ilnitsk.animusic.services.AnimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
@Slf4j
public class AnimeController {
    private final AnimeService animeService;

    @Autowired
    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }


    @GetMapping("/info/{animeId}")
    public Anime getAnimeInfo(@PathVariable Integer animeId) {
        log.info("Requested anime {} info", animeId);
        return animeService.getAnimeInfo(animeId);
    }
    @GetMapping
    public List<Anime> getAllAnime() {
        return animeService.getAllAnime();
    }

    @GetMapping("/navigation")
    public List<AnimeNavDTO> getAnimeDropdownList() {
        return animeService.getAnimeDropdownList();
    }

    @PostMapping("/create")
    public Anime createAnime(@RequestPart(value = "banner") MultipartFile banner,
                             @RequestPart(value = "card") MultipartFile card,
                             @ModelAttribute Anime anime) {
        log.info("Anime {} created", anime.getTitle());
        return animeService.createAnime(anime,banner,card);
    }
    @GetMapping("/images/banner/{id}")
    public ResponseEntity<byte[]> getBanner(@PathVariable("id") Integer animeId) {
        return animeService.getBanner(animeId);
    }
    @PostMapping("/images/banner/{id}")
    public void replaceBanner(@PathVariable("id") Integer animeId,
                              @RequestPart(value = "banner") MultipartFile banner) {
        animeService.replaceBanner(animeId,banner);
    }

    @DeleteMapping("{id}")
    public void deleteAnime(@PathVariable Integer id) {
        animeService.deleteAnime(id);
    }
}
