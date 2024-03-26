package com.ilnitsk.animusic.anime.dto;

import com.ilnitsk.animusic.anime.Anime;
import com.ilnitsk.animusic.playlist.dto.PlaylistDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnimeConverter {
    private final ModelMapper modelMapper;

    public AnimeConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Anime.class,AnimeDto.class);

        modelMapper.createTypeMap(PlaylistDto.class, AlbumDto.class);

    }

    public AnimeDto convertToDto(Anime playlist) {
        return modelMapper.map(playlist,AnimeDto.class);
    }

    public List<AnimeDto> convertListToDto(List<Anime> animeList) {
        return animeList.stream().map(this::convertToDto).toList();
    }
}
