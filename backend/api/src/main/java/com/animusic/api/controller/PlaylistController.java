package com.animusic.api.controller;

import com.animusic.api.dto.CoverArtDto;
import com.animusic.api.dto.PlaylistDto;
import com.animusic.api.mappers.CoverArtMapper;
import com.animusic.api.mappers.PlaylistMapper;
import com.animusic.content.playlist.PlaylistService;
import com.animusic.core.db.model.CoverArt;
import com.animusic.core.db.model.Playlist;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
@Tag(name = "REST API для управления плейлистами", description = "Предоставляет методы для управления плейлистами")
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("{playlistId}")
    @Operation(summary = "Метод для получения плейлиста по Id")
    public PlaylistDto getPlaylistById(@PathVariable Integer playlistId) {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        return PlaylistMapper.convertToDto(playlist);
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

    @PostMapping("cover-art/{playlistId}")
    public CoverArtDto createAlbumCover(
            @PathVariable Integer playlistId,
            @RequestPart(value = "imageFile") MultipartFile imageFile,
            @ModelAttribute AlbumCoverController.CreateCoverDto coverArtDto
    ) {
        CoverArt coverArt = CoverArt.builder()
                .colorLight(coverArtDto.colorLight())
                .colorDark(coverArtDto.colorDark())
                .build();
        CoverArt created = playlistService.createCoverArt(
                playlistId,
                imageFile,
                coverArt);
        return CoverArtMapper.fromCoverArt(created);
    }

}
