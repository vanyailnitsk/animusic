package com.ilnitsk.animusic.user.dto;

import com.ilnitsk.animusic.user.dao.UserPlaylist;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMediaConverter {
    private final ModelMapper modelMapper;

    public UserMediaConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(UserPlaylist.class, UserPlaylistDto.class)
                .addMapping(UserPlaylist::getName, UserPlaylistDto::setName)
                .addMapping(UserPlaylist::getSoundtracks, UserPlaylistDto::setSoundtracks)
                .addMappings(mapper -> mapper.map(UserPlaylist::getSoundtracks, UserPlaylistDto::setSoundtracks));
    }

    public UserPlaylistDto convertToDto(UserPlaylist userPlaylist) {
        return modelMapper.map(userPlaylist,UserPlaylistDto.class);
    }

}
