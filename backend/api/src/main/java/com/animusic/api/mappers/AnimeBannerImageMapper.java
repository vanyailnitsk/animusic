package com.animusic.api.mappers;

import com.animusic.api.dto.AnimeBannerImageDto;
import com.animusic.core.db.model.AnimeBannerImage;
import org.springframework.stereotype.Component;

@Component
public class AnimeBannerImageMapper {

    public AnimeBannerImageDto fromAnimeBanner(AnimeBannerImage animeBannerImage) {
        var image = ImageMapper.fromImage(animeBannerImage.getImage());
        return new AnimeBannerImageDto(
                animeBannerImage.getColor(),
                image
        );
    }
}
