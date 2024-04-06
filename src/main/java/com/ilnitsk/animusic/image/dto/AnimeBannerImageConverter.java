package com.ilnitsk.animusic.image.dto;

import com.ilnitsk.animusic.image.dao.AnimeBannerImage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AnimeBannerImageConverter {
    private final ModelMapper modelMapper;

    public AnimeBannerImageConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(AnimeBannerImage.class, AnimeBannerImageDto.class);
    }

    public AnimeBannerImageDto convertToDto(AnimeBannerImage bannerImage) {
        return modelMapper.map(bannerImage,AnimeBannerImageDto.class);
    }
}
