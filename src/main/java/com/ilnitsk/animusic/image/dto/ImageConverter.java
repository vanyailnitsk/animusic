package com.ilnitsk.animusic.image.dto;

import com.ilnitsk.animusic.image.dao.Image;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {
    private final ModelMapper modelMapper;

    public ImageConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Image.class,ImageDto.class);
    }
}
