package com.animusic.api;

import com.animusic.anime.service.AnimeService;
import com.animusic.api.mappers.AnimeBannerImageConverter;
import com.animusic.api.mappers.ImageConverter;
import com.animusic.image.dao.AnimeBannerImage;
import com.animusic.image.dto.AnimeBannerImageDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/anime/images")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "REST API для управления изображения аниме", description = "Предоставляет методы для информации по аниме")
public class AnimeImageController {

    private final AnimeService animeService;
    private final AnimeBannerImageConverter bannerImageConverter;
    private final ImageConverter imageConverter;

    @PostMapping("/images/banner/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public AnimeBannerImageDto setBanner(@PathVariable("id") Integer animeId,
                                         @RequestPart(value = "banner") MultipartFile banner,
                                         @ModelAttribute AnimeBannerImage bannerImage) {
        AnimeBannerImage bannerCreated = animeService.setBanner(animeId, banner, bannerImage);
        return bannerImageConverter.convertToDto(bannerCreated);
    }
}
