package com.ilnitsk.animusic.user.dto;

import com.ilnitsk.animusic.playlist.dao.Playlist;
import com.ilnitsk.animusic.playlist.dto.UserPlaylistDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMediaConverter {
    private final ModelMapper modelMapper;

    public UserMediaConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Playlist.class, UserPlaylistDto.class)
                .addMapping(Playlist::getName, UserPlaylistDto::setName)
                .addMapping(Playlist::getSoundtracks, UserPlaylistDto::setSoundtracks);
    }

    public UserPlaylistDto convertToDto(Playlist playlist) {
        return modelMapper.map(playlist,UserPlaylistDto.class);
    }

}
