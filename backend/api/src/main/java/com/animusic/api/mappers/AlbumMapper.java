package com.animusic.api.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.animusic.api.dto.AlbumDto;
import com.animusic.api.dto.AlbumItemDto;
import com.animusic.core.db.model.Album;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlbumMapper {

    public static AlbumDto fromAlbum(Album album) {
        var animeRef = album.getAnime();
        var soundtracks = SoundtrackMapper.soundtrackDtos(album.getSoundtracks());
        return new AlbumDto(
                album.getId(),
                album.getName(),
                AnimeMapper.animeItemDto(animeRef),
                soundtracks,
                CoverArtMapper.fromCoverArt(album.getCoverArt())
        );
    }

    public static AlbumItemDto albumItemDto(Album album) {
        var coverArt = CoverArtMapper.fromCoverArt(album.getCoverArt());
        return new AlbumItemDto(album.getId(), album.getName(), coverArt);
    }

    public static List<AlbumDto> convertListToDto(List<Album> albums) {
        return albums.stream().map(AlbumMapper::fromAlbum).toList();
    }

    public static List<AlbumItemDto> albumItems(List<Album> albums) {
        return albums.stream().map(AlbumMapper::albumItemDto).collect(Collectors.toCollection(ArrayList::new));
    }

}
