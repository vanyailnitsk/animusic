package com.animusic.api;

import com.animusic.anime.service.AnimeImageService;
import com.animusic.api.dto.AnimeBannerImageDto;
import com.animusic.api.dto.ImageDto;
import com.animusic.api.mappers.AnimeBannerImageConverter;
import com.animusic.api.mappers.ImageConverter;
import com.animusic.image.dao.AnimeBannerImage;
import com.animusic.image.dao.Image;
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
@Tag(name = "REST API для управления изображениями аниме")
public class AnimeImageController {

    private final AnimeImageService animeImageService;

    private final AnimeBannerImageConverter bannerImageConverter;
    private final ImageConverter imageConverter;

    @PostMapping("banner/{animeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public AnimeBannerImageDto setBanner(@PathVariable("animeId") Integer animeId,
                                         @RequestPart(value = "banner") MultipartFile banner,
                                         @ModelAttribute AnimeBannerImage bannerImage) {
        AnimeBannerImage bannerCreated = animeImageService.setBanner(animeId, banner, bannerImage);
        return bannerImageConverter.convertToDto(bannerCreated);
    }

    @PostMapping("card/{animeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ImageDto setCard(@PathVariable("animeId") Integer animeId,
                            @RequestPart(value = "card") MultipartFile card) {
        Image cardCreated = animeImageService.setCard(animeId, card);
        return imageConverter.convertToDto(cardCreated);
    }
}
