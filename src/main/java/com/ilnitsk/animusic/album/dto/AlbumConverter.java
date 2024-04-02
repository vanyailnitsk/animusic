package com.ilnitsk.animusic.album.dto;

import com.ilnitsk.animusic.album.dao.Album;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlbumConverter {
    private final ModelMapper modelMapper;

    public AlbumConverter(ModelMapper modelMapper) {
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

    public AlbumItemDto convertToItemDto(Album album) {
        return modelMapper.map(album,AlbumItemDto.class);
    }

    public List<AlbumItemDto> convertListToItemDto(List<Album> albums) {
        return albums.stream().map(this::convertToItemDto).toList();
    }

}
