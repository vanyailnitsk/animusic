package com.animusic.api.controller;

import java.util.List;

import com.animusic.api.dto.AnimeDto;
import com.animusic.api.dto.CreateAnimeDto;
import com.animusic.api.dto.RichAnimeDto;
import com.animusic.api.mappers.AnimeMapper;
import com.animusic.content.anime.AnimeNotFoundException;
import com.animusic.content.anime.AnimeService;
import com.animusic.content.subscription.ContentSubscriptionService;
import com.animusic.core.db.model.Anime;
import com.animusic.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/anime")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "REST API для управления аниме", description = "Предоставляет методы для информации по аниме")
public class AnimeController {

    private final AnimeService animeService;

    private final ContentSubscriptionService contentSubscriptionService;

    private final UserService userService;

    @GetMapping("/{animeId}")
    @Operation(summary = "Метод для получения аниме по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение аниме."),
            @ApiResponse(responseCode = "404", description = "Аниме не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public RichAnimeDto getAnimeInfo(@PathVariable Integer animeId) {
        log.info("Requested anime {} info", animeId);
        var anime = animeService.getAnime(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        var response = AnimeMapper.richAnimeDto(anime, userService, contentSubscriptionService);
        var albums = response.albums();
        albums.sort((a1, a2) -> {
            List<String> categoryOrder = List.of("Openings", "Endings", "Themes", "Scene songs");

            int index1 = categoryOrder.indexOf(a1.name());
            int index2 = categoryOrder.indexOf(a2.name());

            return Integer.compare(index1, index2);
        });
        return response;
    }

    @GetMapping
    @Operation(summary = "Метод для получения списка всех аниме.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка аниме"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public List<AnimeDto> getAllAnime() {
        List<Anime> anime = animeService.getAllAnime();
        return anime.stream().map(AnimeMapper::fromAnime).toList();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Метод для создания аниме")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание аниме"),
            @ApiResponse(responseCode = "400", description = "Название аниме занято"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public AnimeDto createAnime(@RequestBody CreateAnimeDto createDto) {
        Anime anime = Anime.builder()
                .title(createDto.title())
                .studio(createDto.studio())
                .releaseYear(createDto.releaseYear())
                .description(createDto.description())
                .folderName(createDto.folderName())
                .build();
        var animeCreated = animeService.createAnime(anime);
        log.info("Anime {} created", anime.getTitle());
        return AnimeMapper.fromAnime(animeCreated);
    }

    @PutMapping("{animeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Метод для обновления аниме")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление аниме"),
            @ApiResponse(responseCode = "404", description = "Аниме не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public RichAnimeDto updateAnime(@RequestBody Anime updateAnime, @PathVariable Integer animeId) {
        Anime anime = animeService.updateAnime(updateAnime, animeId);
        RichAnimeDto richAnimeDto = AnimeMapper.richAnimeDto(anime, userService, contentSubscriptionService);
        log.info("Anime id={} updated successfully", animeId);
        return richAnimeDto;
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
