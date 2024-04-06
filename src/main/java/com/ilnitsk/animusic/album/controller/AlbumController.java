package com.ilnitsk.animusic.album.controller;

import com.ilnitsk.animusic.album.dao.Album;
import com.ilnitsk.animusic.album.dto.*;
import com.ilnitsk.animusic.album.service.AlbumService;
import com.ilnitsk.animusic.image.dao.CoverArt;
import com.ilnitsk.animusic.image.dto.CoverArtConverter;
import com.ilnitsk.animusic.image.dto.CoverArtDto;
import com.ilnitsk.animusic.image.dto.CreateCoverDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "REST API для управления альбомами", description = "Предоставляет методы для управление альбомами")
public class AlbumController {
    private final AlbumService albumService;
    private final AlbumConverter albumConverter;
    private final CoverArtConverter coverArtConverter;
    @GetMapping
    @Operation(summary = "Метод для получения списка альбомов по animeId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение альбомов."),
            @ApiResponse(responseCode = "404", description = "Альбом не найден не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public List<AlbumItemDto> getAlbumsByAnime(@RequestParam("animeId") Integer animeId) {
        log.info("Requested albums by anime {}", animeId);
        List<Album> albums = albumService.getAlbumsByAnimeId(animeId);
        List<AlbumItemDto> albumDtos = albumConverter.convertListToItemDto(albums);
        return albumDtos;
    }

    @GetMapping("{id}")
    @Operation(summary = "Метод для получения альбома по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение альбома"),
            @ApiResponse(responseCode = "404", description = "Альбом не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public AlbumDto getAlbumById(@PathVariable Integer id, HttpServletRequest request) {
        log.info("Requested album with id {}", id);
        Album album = albumService.getAlbumById(id);
        AlbumDto albumDto = albumConverter.convertToDto(album);
        albumDto.setLink(request.getRequestURI());
        return albumDto;
    }


    @PostMapping
    @Operation(summary = "Метод для создания альбома")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание альбома."),
            @ApiResponse(responseCode = "400", description = "Альбом уже существует"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public AlbumDto createAlbum(@RequestBody CreateAlbumDto request) {
        Album album = albumService.createAlbum(request);
        AlbumDto albumDto = albumConverter.convertToDto(album);
        log.info("Album {} in anime {} created",request.getName(),request.getAnimeId());
        return albumDto;
    }

    @PostMapping("cover-art/{albumId}")
    public CoverArtDto createAlbumCover(@PathVariable Integer albumId,
                                        @RequestPart(value = "imageFile") MultipartFile imageFile,
                                        @ModelAttribute CreateCoverDto coverArtDto) {
        CoverArt coverArt = albumService.createCoverArt(albumId,imageFile,coverArtDto);
        return coverArtConverter.convertToDto(coverArt);
    }

    @PutMapping("{albumId}")
    @Operation(summary = "Метод для обновления альбома")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление альбома."),
            @ApiResponse(responseCode = "404", description = "Альбом не найден")
    })
    public AlbumDto updateAlbum(@RequestBody UpdateAlbumDto albumDto, @PathVariable Integer albumId) {
        Album album = albumService.updateAlbum(albumDto,albumId);
        AlbumDto newAlbumDto = albumConverter.convertToDto(album);
        log.info("Album id={} updated successfully",albumId);
        return newAlbumDto;
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление альбома")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаление альбома."),
            @ApiResponse(responseCode = "404", description = "Альбом не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public void deleteAlbum(@PathVariable Integer id) {
        albumService.deleteAlbum(id);
        log.info("Album with id {} deleted",id);
    }
}
