package com.ilnitsk.animusic.playlist.controller;

import com.ilnitsk.animusic.playlist.dao.Playlist;
import com.ilnitsk.animusic.playlist.dto.PlaylistDto;
import com.ilnitsk.animusic.playlist.dto.UserMediaConverter;
import com.ilnitsk.animusic.playlist.service.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
@Tag(name = "REST API для управления плейлистами", description = "Предоставляет методы для управления плейлистами")
public class PlaylistController {
    private final PlaylistService playlistService;
    private final UserMediaConverter userMediaConverter;

    @GetMapping("{playlistId}")
    @Operation(summary = "Метод для получения плейлиста по Id")
    public PlaylistDto getPlaylistById(@PathVariable Long playlistId) {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        return userMediaConverter.convertToDto(playlist);
    }

    @PostMapping
    @Operation(summary = "Метод для создания плейлиста")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public Playlist createPlaylist(@RequestParam String playlistName) {
        return playlistService.createPlaylist(playlistName);
    }

}
