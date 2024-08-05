package com.animusic.api.mappers;

import java.util.List;

import com.animusic.api.dto.AlbumDto;
import com.animusic.api.dto.AlbumItemDto;
import com.animusic.core.db.model.Album;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlbumMapper {

    @NonNull
    private SoundtrackMapper soundtrackMapper;

    @NonNull
    private AnimeMapper animeMapper;

    @NonNull
    private CoverArtMapper coverArtMapper;

    public AlbumDto fromAlbum(Album album) {
        var animeRef = album.getAnime();
        var soundtracks = soundtrackMapper.soundtrackDtos(album.getSoundtracks());
        return new AlbumDto(
                album.getId(),
                album.getName(),
                animeMapper.animeItemDto(animeRef),
                soundtracks,
                coverArtMapper.fromCoverArt(album.getCoverArt())
        );
    }

    public AlbumItemDto albumItemDto(Album album) {
        var coverArt = coverArtMapper.fromCoverArt(album.getCoverArt());
        return new AlbumItemDto(album.getId(), album.getName(), coverArt);
    }

    public List<AlbumDto> convertListToDto(List<Album> albums) {
        return albums.stream().map(this::fromAlbum).toList();
    }

    public List<AlbumItemDto> albumItems(List<Album> albums) {
        return albums.stream().map(this::albumItemDto).toList();
    }

}
