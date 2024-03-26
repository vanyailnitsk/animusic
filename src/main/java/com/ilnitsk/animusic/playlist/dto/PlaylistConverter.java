package com.ilnitsk.animusic.playlist.dto;

import com.ilnitsk.animusic.playlist.Playlist;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaylistConverter {
    private final ModelMapper modelMapper;

    public PlaylistConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Playlist.class,PlaylistDto.class)
                .addMapping(Playlist::getBannerLink, PlaylistDto::setBannerLink);
        modelMapper.createTypeMap(Playlist.class,PlaylistItemDto.class);
    }

    public PlaylistDto convertToDto(Playlist playlist) {
        return modelMapper.map(playlist,PlaylistDto.class);
    }

    public List<PlaylistDto> convertListToDto(List<Playlist> playlists) {
        return playlists.stream().map(this::convertToDto).toList();
    }
}
