package animusic.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import animusic.api.dto.PlaylistDto;
import animusic.api.mappers.PlaylistMapper;
import animusic.core.db.model.Playlist;
import animusic.service.playlist.MediaLibraryService;

@RestController
@RequestMapping("/api/collection")
@RequiredArgsConstructor
@Tag(name = "REST API для управления медиатекой пользователя", description = "Предоставляет методы для управления " +
        "медиатекой пользователя")
public class MediaLibraryController {

    private final MediaLibraryService mediaLibraryService;

    @GetMapping
    @Operation(summary = "Метод для получения списка любимых треков")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка любимых треков"),
            @ApiResponse(responseCode = "401", description = "Не авторизован"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public PlaylistDto getFavouriteTracksPlaylist() {
        Playlist playlist = mediaLibraryService.getFavouritePlaylistOrCreate();
        PlaylistDto dto = PlaylistMapper.convertToDto(playlist);
        return dto;
    }

    @PostMapping
    @Operation(summary = "Метод для получения добавления трека в любимые")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное добавление"),
            @ApiResponse(responseCode = "404", description = "Саундтрек не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public void addTrackToFavourites(@RequestParam Integer trackId) {
        mediaLibraryService.addTrackToFavourites(trackId);
    }

    @DeleteMapping
    @Operation(summary = "Метод для удаления трека из любимых")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаление"),
            @ApiResponse(responseCode = "404", description = "Саундтрек не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public void deleteTrackFromFavourites(@RequestParam Integer trackId) {
        mediaLibraryService.deleteTrackFromFavourites(trackId);
    }


}
