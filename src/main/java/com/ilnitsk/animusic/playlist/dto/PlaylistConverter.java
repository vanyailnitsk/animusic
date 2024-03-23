package com.ilnitsk.animusic.playlist.dto;

import com.ilnitsk.animusic.playlist.Playlist;
import com.ilnitsk.animusic.soundtrack.SoundtrackDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlaylistConverter {
    private final ModelMapper modelMapper;

    public PlaylistConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Playlist.class,PlaylistDto.class)
                .addMapping(Playlist::getBannerLink, PlaylistDto::setBannerLink)
                .addMappings(mapper -> mapper.skip(PlaylistDto::setSoundtracks));
    }

    public PlaylistDto convertToDto(Playlist playlist) {
        PlaylistDto dto = modelMapper.map(playlist,PlaylistDto.class);
        dto.setSoundtracks(playlist.getSoundtracks().stream()
                .map(SoundtrackDto::new)
                .toList());
        return dto;
    }
}
