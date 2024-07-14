package com.animusic.api;

import com.animusic.album.service.AlbumService;
import com.animusic.anime.service.AnimeService;
import com.animusic.api.dto.AnimeDto;
import com.animusic.api.dto.AnimeMapper;
import com.animusic.api.dto.RichAnimeDto;
import com.animusic.api.dto.UpdateAnimeDto;
import com.animusic.core.db.model.Album;
import com.animusic.core.db.model.Anime;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "REST API для управления аниме", description = "Предоставляет методы для информации по аниме")
public class AnimeController {
    private final AnimeService animeService;
    private final AnimeMapper animeMapper;
    private final AlbumService albumService;

    @GetMapping("/{animeId}")
    @Operation(summary = "Метод для получения аниме по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение аниме."),
            @ApiResponse(responseCode = "404", description = "Аниме не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public RichAnimeDto getAnimeInfo(@PathVariable Integer animeId) {
        log.info("Requested anime {} info", animeId);
        Anime anime = animeService.getAnimeInfo(animeId);
        List<Album> albums = albumService.getAlbumsByAnimeId(animeId);
        if (albums!= null) {
            albums.sort((a1,a2) -> {
                List<String> categoryOrder = List.of("Openings", "Endings", "Themes", "Scene songs");

                int index1 = categoryOrder.indexOf(a1.getName());
                int index2 = categoryOrder.indexOf(a2.getName());

                return Integer.compare(index1, index2);
            });
        }
        return RichAnimeDto.fromAnime(anime,albums);
    }

    @GetMapping
    @Operation(summary = "Метод для получения списка всех аниме.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка аниме"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public List<AnimeDto> getAllAnime() {
        List<Anime> anime = animeService.getAllAnime();
        return anime.stream().map(AnimeDto::fromAnime).toList();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Метод для создания аниме")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание аниме"),
            @ApiResponse(responseCode = "400", description = "Название аниме занято"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public AnimeDto createAnime(@RequestBody UpdateAnimeDto createDto) {
        Anime anime = animeMapper.convertToEntity(createDto);
        anime = animeService.createAnime(anime);
        log.info("Anime {} created", anime.getTitle());
        return AnimeDto.fromAnime(anime);
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
        List<Album> albums = albumService.getAlbumsByAnimeId(animeId);
        RichAnimeDto richAnimeDto = RichAnimeDto.fromAnime(anime,albums);
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
