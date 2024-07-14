package com.animusic.api.mappers;

import com.animusic.api.dto.ImageDto;
import com.animusic.core.db.model.Image;
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
        return modelMapper.map(image, ImageDto.class);
    }
}
