package com.animusic.api.mappers;

import com.animusic.api.dto.AnimeDto;
import com.animusic.api.dto.AnimeItemDto;
import com.animusic.api.dto.RichAnimeDto;
import com.animusic.core.db.model.Anime;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnimeMapper {

    @NonNull
    private ImageMapper imageMapper;

    @NonNull
    private AlbumMapper albumMapper;

    public AnimeDto fromAnime(Anime anime) {
        return new AnimeDto(
                anime.getId(),
                anime.getTitle(),
                anime.getStudio(),
                anime.getReleaseYear(),
                anime.getDescription(),
                anime.getFolderName(),
                imageMapper.fromAnimeBanner(anime.getBannerImage()),
                imageMapper.fromImage(anime.getCardImage())
        );
    }

    public AnimeItemDto animeItemDto(Anime anime) {
        return new AnimeItemDto(anime.getId(), anime.getTitle());
    }

    public RichAnimeDto richAnimeDto(Anime anime) {
        return new RichAnimeDto(
                anime.getId(),
                anime.getTitle(),
                anime.getStudio(),
                anime.getReleaseYear(),
                anime.getDescription(),
                anime.getFolderName(),
                imageMapper.fromAnimeBanner(anime.getBannerImage()),
                imageMapper.fromImage(anime.getCardImage()),
                albumMapper.albumItems(anime.getAlbums())
        );
    }
}
