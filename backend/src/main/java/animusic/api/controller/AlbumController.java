package animusic.api.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import animusic.api.dto.AlbumDto;
import animusic.api.dto.AlbumItemDto;
import animusic.api.mappers.AlbumMapper;
import animusic.core.db.model.Album;
import animusic.service.album.AlbumService;
import animusic.service.anime.AnimeNotFoundException;
import animusic.service.security.UserService;
import animusic.service.subscription.ContentSubscriptionService;

@RestController
@RequestMapping("/api/albums")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "REST API для управления альбомами", description = "Предоставляет методы для управление альбомами")
public class AlbumController {

    private final AlbumService albumService;

    private final UserService userService;

    private final ContentSubscriptionService contentSubscriptionService;

    @GetMapping
    @Operation(summary = "Метод для получения списка альбомов по animeId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение альбомов."),
            @ApiResponse(responseCode = "404", description = "Альбом не найден не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public List<AlbumItemDto> getAlbumsByAnime(@RequestParam("animeId") Integer animeId) {
        log.info("Requested albums by anime {}", animeId);
        var albums = albumService.getAlbumsByAnimeId(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        var albumItems = AlbumMapper.albumItems(albums);
        albumItems.sort((a1, a2) -> {
            List<String> categoryOrder = List.of("Openings", "Endings", "Themes", "Scene songs");

            int index1 = categoryOrder.indexOf(a1.name());
            int index2 = categoryOrder.indexOf(a2.name());

            return Integer.compare(index1, index2);
        });
        return albumItems;
    }

    @GetMapping("{id}")
    @Operation(summary = "Метод для получения альбома по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение альбома"),
            @ApiResponse(responseCode = "404", description = "Альбом не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public AlbumDto getAlbumById(@PathVariable Integer id) {
        log.info("Requested album with id {}", id);
        var album = albumService.getAlbumById(id);
        return AlbumMapper.fromAlbum(album, userService, contentSubscriptionService);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Метод для создания альбома")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание альбома."),
            @ApiResponse(responseCode = "400", description = "Альбом уже существует"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public AlbumDto createAlbum(@RequestBody CreateAlbumDto request) {
        var album = albumService.createAlbum(request.toAlbum(), request.animeId);
        var albumDto = AlbumMapper.fromAlbum(album, userService, contentSubscriptionService);
        log.info("Album {} in anime {} created", album.getName(), request.animeId);
        return albumDto;
    }

    @PutMapping("{albumId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Метод для обновления альбома")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление альбома."),
            @ApiResponse(responseCode = "404", description = "Альбом не найден")
    })
    public AlbumDto updateAlbumName(@RequestBody UpdateAlbumDto albumDto, @PathVariable Integer albumId) {
        Album album = albumService.updateAlbumName(albumDto.name(), albumId);
        AlbumDto newAlbumDto = AlbumMapper.fromAlbum(album, userService, contentSubscriptionService);
        log.info("Album id={} updated successfully", albumId);
        return newAlbumDto;
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Удаление альбома")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаление альбома."),
            @ApiResponse(responseCode = "404", description = "Альбом не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public void deleteAlbum(@PathVariable Integer id) {
        albumService.deleteAlbum(id);
        log.info("Album with id {} deleted", id);
    }

    record CreateAlbumDto(
            String name,
            Integer animeId
    ) {
        public Album toAlbum() {
            return Album.builder()
                    .name(name)
                    .build();
        }
    }

    record UpdateAlbumDto(String name, String imageUrl) {
    }
}
