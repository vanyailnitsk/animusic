package com.ilnitsk.animusic.playlist;

import com.ilnitsk.animusic.anime.AnimeRepository;
import com.ilnitsk.animusic.playlist.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "REST API для управления альбомами", description = "Предоставляет методы для управление альбомами")
public class PlaylistController {
    private final PlaylistService playlistService;
    private final AnimeRepository animeRepository;
    private final PlaylistConverter playlistConverter;
    @GetMapping
    @Operation(summary = "Метод для получения списка альбомов по animeId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение альбомов."),
            @ApiResponse(responseCode = "404", description = "Альбом не найден не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public List<AlbumItemDto> getPlaylistsByAnime(@RequestParam("animeId") Integer animeId) {
        log.info("Requested playlists by anime {}", animeId);
        List<Album> albums = playlistService.getPlaylistsByAnimeId(animeId);
        List<AlbumItemDto> albumDtos = playlistConverter.convertListToItemDto(albums);
        return albumDtos;
    }

    @GetMapping("{id}")
    @Operation(summary = "Метод для получения альбома по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение альбома"),
            @ApiResponse(responseCode = "404", description = "Альбом не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public AlbumDto getPlaylistById(@PathVariable Integer id, HttpServletRequest request) {
        log.info("Requested playlist with id {}", id);
        Album album = playlistService.getPlaylistById(id);
        AlbumDto albumDto = playlistConverter.convertToDto(album);
        albumDto.setLink(request.getRequestURI());
        return albumDto;
    }

    @GetMapping("/images/banner/{id}")
    public ResponseEntity<byte[]> getBanner(@PathVariable("id") Integer playlistId) {
        return playlistService.getBanner(playlistId);
    }

    @PostMapping
    @Operation(summary = "Метод для создания альбома")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание альбома."),
            @ApiResponse(responseCode = "400", description = "Альбом уже существует"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public Album createPlaylist(@RequestBody CreatePlaylistRequest request) {
        Album album = playlistService.createPlaylist(request);

        log.info("Playlist {} in anime {} created",request.getName(),request.getAnimeId());
        return album;
    }

    @PutMapping("{playlistId}")
    @Operation(summary = "Метод для обновления альбома")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление альбома."),
            @ApiResponse(responseCode = "404", description = "Альбом не найден")
    })
    public AlbumDto updatePlaylist(@RequestBody UpdatePlaylistDto playlistDto, @PathVariable Integer playlistId) {
        Album album = playlistService.updatePlaylist(playlistDto,playlistId);
        AlbumDto newAlbumDto = playlistConverter.convertToDto(album);
        log.info("Playlist id={} updated successfully",playlistId);
        return newAlbumDto;
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление альбома")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаление альбома."),
            @ApiResponse(responseCode = "404", description = "Альбом не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public void deletePlaylist(@PathVariable Integer id) {
        playlistService.deletePlaylist(id);
        log.info("Playlist with id {} deleted",id);
    }
}
