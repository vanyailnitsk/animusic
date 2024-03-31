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

        modelMapper.createTypeMap(Album.class,PlaylistDto.class)
                .addMapping(Album::getBannerLink, PlaylistDto::setBannerLink);
        modelMapper.createTypeMap(Album.class,PlaylistItemDto.class);
    }

    public PlaylistDto convertToDto(Album album) {
        return modelMapper.map(album,PlaylistDto.class);
    }

    public List<PlaylistDto> convertListToDto(List<Album> albums) {
        return albums.stream().map(this::convertToDto).toList();
    }
}
