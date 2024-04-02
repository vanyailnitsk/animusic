package com.ilnitsk.animusic.anime.controller;

import com.ilnitsk.animusic.anime.dao.Anime;
import com.ilnitsk.animusic.anime.dto.AnimeConverter;
import com.ilnitsk.animusic.anime.dto.AnimeDto;
import com.ilnitsk.animusic.anime.dto.UpdateAnimeDto;
import com.ilnitsk.animusic.anime.service.AnimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "REST API для управления аниме", description = "Предоставляет методы для информации по аниме")
public class AnimeController {
    private final AnimeService animeService;
    private final AnimeConverter animeConverter;

    @GetMapping("/{animeId}")
    @Operation(summary = "Метод для получения аниме по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение аниме."),
            @ApiResponse(responseCode = "404", description = "Аниме не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public AnimeDto getAnimeInfo(@PathVariable Integer animeId) {
        log.info("Requested anime {} info", animeId);
        Anime anime = animeService.getAnimeInfo(animeId);
        return animeConverter.convertToDto(anime);
    }
    @GetMapping
    @Operation(summary = "Метод для получения списка всех аниме.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка аниме"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public List<AnimeDto> getAllAnime() {
        List<Anime> anime = animeService.getAllAnime();
        return animeConverter.convertListToDto(anime);
    }

    @PostMapping
    @Operation(summary = "Метод для создания аниме")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание аниме"),
            @ApiResponse(responseCode = "400", description = "Название аниме занято"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public AnimeDto createAnime(@RequestPart(value = "banner") MultipartFile banner,
                             @RequestPart(value = "card") MultipartFile card,
                             @ModelAttribute Anime anime) {
        log.info("Anime {} created", anime.getTitle());
        Anime createdAnime = animeService.createAnime(anime,banner,card);
        return animeConverter.convertToDto(createdAnime);
    }

    @PutMapping("{animeId}")
    @Operation(summary = "Метод для обновления аниме")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление аниме"),
            @ApiResponse(responseCode = "404", description = "Аниме не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public AnimeDto updateAnime(@RequestBody UpdateAnimeDto updateAnimeDto,@PathVariable Integer animeId) {
        Anime anime = animeService.updateAnime(updateAnimeDto,animeId);
        AnimeDto animeDto = animeConverter.convertToDto(anime);
        log.info("Anime id={} updated successfully",animeId);
        return animeDto;
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
    @Operation(summary = "Метод для удаления аниме")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаление."),
            @ApiResponse(responseCode = "400", description = "Аниме не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public void deleteAnime(@PathVariable Integer id) {
        animeService.deleteAnime(id);
    }
}
