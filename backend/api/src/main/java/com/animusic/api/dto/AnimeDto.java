package com.animusic.api.dto;

import com.animusic.core.db.model.Anime;
import lombok.NonNull;

public record AnimeDto(
        Integer id,
        String title,
        Integer releaseYear,
        String description,
        String folderName,
        AnimeBannerImageDto banner,
        ImageDto cardImage
) {
    public static AnimeDto fromAnime(@NonNull Anime anime) {
        return new AnimeDto(
                anime.getId(),
                anime.getTitle(),
                anime.getReleaseYear(),
                anime.getDescription(),
                anime.getFolderName(),
                AnimeBannerImageDto.fromAnimeBannerImage(anime.getBannerImage()),
                ImageDto.fromImage(anime.getCardImage())
        );
    }

}
