package com.animusic.api;

import com.animusic.anime.dao.Anime;
import com.animusic.anime.dto.AnimeConverter;
import com.animusic.anime.dto.AnimeDto;
import com.animusic.anime.dto.UpdateAnimeDto;
import com.animusic.anime.service.AnimeService;
import com.ilnitsk.animusic.image.dao.AnimeBannerImage;
import com.ilnitsk.animusic.image.dao.Image;
import com.ilnitsk.animusic.image.dto.AnimeBannerImageConverter;
import com.ilnitsk.animusic.image.dto.AnimeBannerImageDto;
import com.ilnitsk.animusic.image.dto.ImageConverter;
import com.ilnitsk.animusic.image.dto.ImageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final AnimeBannerImageConverter bannerImageConverter;
    private final ImageConverter imageConverter;

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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Метод для создания аниме")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание аниме"),
            @ApiResponse(responseCode = "400", description = "Название аниме занято"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public AnimeDto createAnime(@RequestBody UpdateAnimeDto createDto) {
        Anime anime = animeConverter.convertToEntity(createDto);
        anime = animeService.createAnime(anime);
        log.info("Anime {} created", anime.getTitle());
        return animeConverter.convertToDto(anime);
    }

    @PutMapping("{animeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

    @PostMapping("/images/banner/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public AnimeBannerImageDto setBanner(@PathVariable("id") Integer animeId,
                                         @RequestPart(value = "banner") MultipartFile banner,
                                         @ModelAttribute AnimeBannerImage bannerImage) {
        AnimeBannerImage bannerCreated = animeService.setBanner(animeId,banner,bannerImage);
        return bannerImageConverter.convertToDto(bannerCreated);
    }

    @PostMapping("/images/card/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ImageDto setCard(@PathVariable("id") Integer animeId,
                            @RequestPart(value = "card") MultipartFile card) {
        Image cardCreated = animeService.setCard(animeId,card);
        return imageConverter.convertToDto(cardCreated);
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
