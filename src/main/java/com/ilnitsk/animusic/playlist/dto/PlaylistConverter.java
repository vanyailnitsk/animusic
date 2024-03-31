package com.ilnitsk.animusic.playlist.dto;

import com.ilnitsk.animusic.playlist.Album;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaylistConverter {
    private final ModelMapper modelMapper;

    public PlaylistConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Album.class, AlbumDto.class)
                .addMapping(Album::getBannerLink, AlbumDto::setBannerLink);
        modelMapper.createTypeMap(Album.class, AlbumItemDto.class);
        modelMapper.createTypeMap(AlbumDto.class,AlbumItemDto.class);
    }

    public AlbumDto convertToDto(Album album) {
        return modelMapper.map(album, AlbumDto.class);
    }

    public List<AlbumDto> convertListToDto(List<Album> albums) {
        return albums.stream().map(this::convertToDto).toList();
    }
}
