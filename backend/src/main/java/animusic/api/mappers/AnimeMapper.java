package animusic.api.mappers;

import org.springframework.stereotype.Component;

import animusic.api.dto.AnimeDto;
import animusic.api.dto.AnimeItemDto;
import animusic.api.dto.RichAnimeDto;
import animusic.core.db.model.Anime;
import animusic.core.db.model.User;
import animusic.service.subscription.ContentSubscribedHelper;
import animusic.service.subscription.ContentSubscriptionService;

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
            User user,
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
                ContentSubscribedHelper.isSubscribedToAnime(user, contentSubscriptionService, anime.getId()),
                AlbumMapper.albumItems(anime.getAlbums())
        );
    }
}
