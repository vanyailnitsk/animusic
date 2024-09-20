package com.animusic.api.mappers;

import com.animusic.api.dto.AnimeDto;
import com.animusic.api.dto.AnimeItemDto;
import com.animusic.api.dto.RichAnimeDto;
import com.animusic.content.subscription.ContentSubscribedHelper;
import com.animusic.content.subscription.ContentSubscriptionService;
import com.animusic.core.db.model.Anime;
import com.animusic.user.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class AnimeMapper {

    public static AnimeDto fromAnime(Anime anime) {
        return new AnimeDto(
                anime.getId(),
                anime.getTitle(),
                anime.getStudio(),
                anime.getReleaseYear(),
                anime.getDescription(),
                anime.getFolderName(),
                ImageMapper.fromAnimeBanner(anime.getBannerImage()),
                ImageMapper.fromImage(anime.getCardImage())
        );
    }

    public static AnimeItemDto animeItemDto(Anime anime) {
        return new AnimeItemDto(anime.getId(), anime.getTitle());
    }

    public static RichAnimeDto richAnimeDto(
            Anime anime,
            UserService userService,
            ContentSubscriptionService contentSubscriptionService
    ) {
        return new RichAnimeDto(
                anime.getId(),
                anime.getTitle(),
                anime.getStudio(),
                anime.getReleaseYear(),
                anime.getDescription(),
                anime.getFolderName(),
                ImageMapper.fromAnimeBanner(anime.getBannerImage()),
                ImageMapper.fromImage(anime.getCardImage()),
                ContentSubscribedHelper.isSubscribedToAnime(userService, contentSubscriptionService, anime.getId()),
                AlbumMapper.albumItems(anime.getAlbums())
        );
    }
}
