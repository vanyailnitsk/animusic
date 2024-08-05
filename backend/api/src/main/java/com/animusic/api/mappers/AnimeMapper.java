package com.animusic.api.mappers;

import com.animusic.api.dto.AnimeDto;
import com.animusic.api.dto.AnimeItemDto;
import com.animusic.api.dto.RichAnimeDto;
import com.animusic.core.db.model.Anime;
import org.springframework.stereotype.Component;

@Component
public class AnimeMapper {

    public AnimeDto fromAnime(Anime anime) {
        return new AnimeDto(
                anime.getId(),
                anime.getTitle(),
                anime.getStudio(),
                anime.getReleaseYear(),
                anime.getDescription(),
                anime.getFolderName(),
                AnimeBannerImageMapper.fromAnimeBanner(anime.getBannerImage()),
                ImageMapper.fromImage(anime.getCardImage())
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
                AnimeBannerImageMapper.fromAnimeBanner(anime.getBannerImage()),
                ImageMapper.fromImage(anime.getCardImage()),
                AlbumMapper.albumItems(anime.getAlbums())
        );
    }
}
