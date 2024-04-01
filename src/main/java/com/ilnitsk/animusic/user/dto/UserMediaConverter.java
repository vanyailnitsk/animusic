package com.ilnitsk.animusic.user.dto;

import com.ilnitsk.animusic.playlist.dao.Playlist;
import com.ilnitsk.animusic.playlist.dto.PlaylistDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMediaConverter {
    private final ModelMapper modelMapper;

    public UserMediaConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Playlist.class, PlaylistDto.class)
                .addMapping(Playlist::getName, PlaylistDto::setName)
                .addMapping(Playlist::getSoundtracks, PlaylistDto::setSoundtracks);
    }

    public PlaylistDto convertToDto(Playlist playlist) {
        return modelMapper.map(playlist, PlaylistDto.class);
    }

}
