package com.animusic.api.dto;

import com.animusic.core.db.model.Album;
import com.animusic.core.db.model.Anime;

import java.util.List;

public record RichAnimeDto(
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
    public static RichAnimeDto fromAnime(Anime anime, List<Album> albums) {
        if (albums == null) {
            albums = List.of();
        }
        return new RichAnimeDto(
                anime.getId(),
                anime.getTitle(),
                anime.getStudio(),
                anime.getReleaseYear(),
                anime.getDescription(),
                anime.getFolderName(),
                AnimeBannerImageDto.fromAnimeBannerImage(anime.getBannerImage()),
                ImageDto.fromImage(anime.getCardImage()),
                albums.stream().map(AlbumItemDto::fromAlbum).toList()
        );
    }
}
