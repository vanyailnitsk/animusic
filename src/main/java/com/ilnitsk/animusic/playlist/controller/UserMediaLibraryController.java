package com.ilnitsk.animusic.playlist.controller;

import com.ilnitsk.animusic.playlist.dao.Playlist;
import com.ilnitsk.animusic.playlist.dto.PlaylistDto;
import com.ilnitsk.animusic.playlist.dto.UserMediaConverter;
import com.ilnitsk.animusic.playlist.service.UserMediaLibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-media-library")
@RequiredArgsConstructor
@Tag(name = "REST API для управления медиатекой пользователя", description = "Предоставляет методы для управления медиатекой пользователя")
public class UserMediaLibraryController {
    private final UserMediaLibraryService userMediaLibraryService;
    private final UserMediaConverter userMediaConverter;

    @GetMapping("/favourites")
    @Operation(summary = "Метод для получения списка любимых треков")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка любимых треков"),
            @ApiResponse(responseCode = "401", description = "Не авторизован"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public PlaylistDto getFavouriteTracksPlaylist(HttpServletRequest request) {
        Playlist playlist = userMediaLibraryService.getFavouriteTracksPlaylist();
        PlaylistDto dto = userMediaConverter.convertToDto(playlist);
        dto.setLink(request.getRequestURI());
        return dto;
    }

    @PostMapping("/favourites")
    @Operation(summary = "Метод для получения добавления трека в любимые")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное добавление"),
            @ApiResponse(responseCode = "404", description = "Саундтрек не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public void addTrackToFavourites(@RequestParam Integer trackId) {
        userMediaLibraryService.addTrackToFavourites(trackId);
    }

    @DeleteMapping("/favourites")
    @Operation(summary = "Метод для удаления трека из любимых")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаление"),
            @ApiResponse(responseCode = "404", description = "Саундтрек не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public void deleteTrackFromFavourites(@RequestParam Integer trackId) {
        userMediaLibraryService.deleteTrackFromFavourites(trackId);
    }


}
