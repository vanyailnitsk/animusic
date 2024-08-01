package com.animusic.api;

import com.animusic.api.dto.AnimeBannerImageDto;
import com.animusic.api.dto.ImageDto;
import com.animusic.api.mappers.AnimeBannerImageConverter;
import com.animusic.api.mappers.ImageConverter;
import com.animusic.content.anime.AnimeImageService;
import com.animusic.core.db.model.AnimeBannerImage;
import com.animusic.core.db.model.Image;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
    public AnimeBannerImageDto setBanner(
            @PathVariable("animeId") Integer animeId,
            @RequestPart(value = "banner") MultipartFile banner,
            @ModelAttribute AnimeBannerImage bannerImage
    ) {
        AnimeBannerImage bannerCreated = animeImageService.setBanner(animeId, banner, bannerImage);
        return bannerImageConverter.convertToDto(bannerCreated);
    }

    @PostMapping("card/{animeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ImageDto setCard(
            @PathVariable("animeId") Integer animeId,
            @RequestPart(value = "card") MultipartFile card
    ) {
        Image cardCreated = animeImageService.setCard(animeId, card);
        return imageConverter.convertToDto(cardCreated);
    }
}
