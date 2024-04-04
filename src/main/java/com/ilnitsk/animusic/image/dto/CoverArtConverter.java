package com.ilnitsk.animusic.image.dto;

import com.ilnitsk.animusic.image.dao.CoverArt;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CoverArtConverter {
    private final ModelMapper modelMapper;

    public CoverArtConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(CoverArt.class, CoverArtDto.class)
                .addMapping(CoverArt::getColorDark,CoverArtDto::setColorDark)
                .addMapping(CoverArt::getColorLight,CoverArtDto::setColorLight);

        modelMapper.createTypeMap(CreateCoverDto.class,CoverArt.class);
    }

    public CoverArt convertToEntity(CreateCoverDto dto) {
        return modelMapper.map(dto,CoverArt.class);
    }
}
