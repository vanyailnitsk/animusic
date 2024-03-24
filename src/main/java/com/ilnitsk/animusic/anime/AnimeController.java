package com.ilnitsk.animusic.anime;

import com.ilnitsk.animusic.anime.dto.AnimeConverter;
import com.ilnitsk.animusic.anime.dto.AnimeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
@Slf4j
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeService animeService;
    private final AnimeConverter animeConverter;

    @GetMapping("/{animeId}")
    public AnimeDto getAnimeInfo(@PathVariable Integer animeId) {
        log.info("Requested anime {} info", animeId);
        Anime anime = animeService.getAnimeInfo(animeId);
        return animeConverter.convertToDto(anime);
    }
    @GetMapping
    public List<AnimeDto> getAllAnime() {
        List<Anime> anime = animeService.getAllAnime();
        return animeConverter.convertListToDto(anime);
    }

    @PostMapping
    public AnimeDto createAnime(@RequestPart(value = "banner") MultipartFile banner,
                             @RequestPart(value = "card") MultipartFile card,
                             @ModelAttribute Anime anime) {
        log.info("Anime {} created", anime.getTitle());
        Anime createdAnime = animeService.createAnime(anime,banner,card);
        return animeConverter.convertToDto(createdAnime);
    }
    @GetMapping("/images/banner/{id}")
    public ResponseEntity<byte[]> getBanner(@PathVariable("id") Integer animeId) {
        return animeService.getBanner(animeId);
    }
    @PostMapping("/images/banner/{id}")
    public void setBanner(@PathVariable("id") Integer animeId,
                              @RequestPart(value = "banner") MultipartFile banner) {
        animeService.setBanner(animeId,banner);
    }

    @GetMapping("/images/card/{id}")
    public ResponseEntity<byte[]> getCard(@PathVariable("id") Integer animeId) {
        return animeService.getCard(animeId);
    }
    @PostMapping("/images/card/{id}")
    public void setCard(@PathVariable("id") Integer animeId,
                              @RequestPart(value = "card") MultipartFile card) {
        animeService.setCard(animeId,card);
    }

    @DeleteMapping("{id}")
    public void deleteAnime(@PathVariable Integer id) {
        animeService.deleteAnime(id);
    }
}
