package com.ilnitsk.animusic.image;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CoverArtConverter {
    private final ModelMapper modelMapper;

    public CoverArtConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(CoverArt.class,CoverArtDto.class)
                .addMapping(CoverArt::getImageUrl,CoverArtDto::setImageUrl)
                .addMapping(CoverArt::getColorDark,CoverArtDto::setColorDark)
                .addMapping(CoverArt::getColorLight,CoverArtDto::setColorLight);
    }
}
