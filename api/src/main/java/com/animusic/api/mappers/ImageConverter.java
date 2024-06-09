package com.animusic.api.mappers;

import com.animusic.image.dao.Image;
import com.animusic.image.dto.ImageDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {
    private final ModelMapper modelMapper;

    public ImageConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Image.class, ImageDto.class);
    }

    public ImageDto convertToDto(Image image) {
        return modelMapper.map(image,ImageDto.class);
    }
}
