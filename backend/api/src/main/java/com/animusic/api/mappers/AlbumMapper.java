package com.animusic.api.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.animusic.api.dto.AlbumDto;
import com.animusic.api.dto.AlbumItemDto;
import com.animusic.content.subscription.ContentSubscribedHelper;
import com.animusic.content.subscription.ContentSubscriptionService;
import com.animusic.core.db.model.Album;
import com.animusic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlbumMapper {

    public static AlbumDto fromAlbum(
            Album album,
            UserService userService,
            ContentSubscriptionService contentSubscriptionService
    ) {
        var animeRef = album.getAnime();
        var soundtracks = SoundtrackMapper.soundtrackDtos(album.getSoundtracks());
        return new AlbumDto(
                album.getId(),
                album.getName(),
                AnimeMapper.animeItemDto(animeRef),
                CoverArtMapper.fromCoverArt(album.getCoverArt()),
                ContentSubscribedHelper.isSubscribedToAlbum(userService, contentSubscriptionService, album.getId()),
                soundtracks
        );
    }

    public static AlbumItemDto albumItemDto(Album album) {
        var coverArt = CoverArtMapper.fromCoverArt(album.getCoverArt());
        return new AlbumItemDto(album.getId(), album.getName(), coverArt);
    }

    public static List<AlbumItemDto> albumItems(List<Album> albums) {
        return albums.stream().map(AlbumMapper::albumItemDto).collect(Collectors.toCollection(ArrayList::new));
    }

}
