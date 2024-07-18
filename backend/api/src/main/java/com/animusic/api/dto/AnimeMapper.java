package com.animusic.api.dto;

import com.animusic.core.db.model.Anime;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AnimeMapper {
    private final ModelMapper modelMapper;

    public AnimeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Anime.class, RichAnimeDto.class);

        modelMapper.createTypeMap(Anime.class, AnimeItemDto.class);
        modelMapper.createTypeMap(UpdateAnimeDto.class, Anime.class);
    }

    public Anime convertToEntity(UpdateAnimeDto dto) {
        return modelMapper.map(dto, Anime.class);
    }
}
