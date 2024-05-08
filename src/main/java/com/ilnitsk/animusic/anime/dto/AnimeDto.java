package com.ilnitsk.animusic.anime.dto;

import com.ilnitsk.animusic.album.dto.AlbumItemDto;
import com.ilnitsk.animusic.image.dto.AnimeBannerImageDto;
import com.ilnitsk.animusic.image.dto.ImageDto;

import java.util.List;

public record AnimeDto(
        Integer id,
        String title,
        String studio,
        Integer releaseYear,
        String description,
        String folderName,
        AnimeBannerImageDto banner,
        ImageDto cardImage,
        List<AlbumItemDto> albums
) {
}
