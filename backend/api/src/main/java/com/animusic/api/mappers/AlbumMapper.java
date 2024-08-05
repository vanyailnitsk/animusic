package com.animusic.api.mappers;

import java.util.List;

import com.animusic.api.dto.AlbumDto;
import com.animusic.api.dto.AlbumItemDto;
import com.animusic.core.db.model.Album;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper {

    public AlbumDto fromAlbum(Album album) {
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

    public AlbumItemDto albumItemDto(Album album) {
        var coverArt = CoverArtMapper.fromCoverArt(album.getCoverArt());
        return new AlbumItemDto(album.getId(), album.getName(), coverArt);
    }

    public List<AlbumDto> convertListToDto(List<Album> albums) {
        return albums.stream().map(AlbumMapper::fromAlbum).toList();
    }

    public List<AlbumItemDto> albumItems(List<Album> albums) {
        return albums.stream().map(AlbumMapper::albumItemDto).toList();
    }

}
