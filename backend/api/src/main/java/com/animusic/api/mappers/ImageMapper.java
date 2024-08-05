package com.animusic.api.mappers;

import com.animusic.api.dto.AnimeBannerImageDto;
import com.animusic.api.dto.ImageDto;
import com.animusic.core.db.model.AnimeBannerImage;
import com.animusic.core.db.model.Image;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    @Nullable
    public ImageDto fromImage(@Nullable Image image) {
        if (image == null) {
            return null;
        }
        return new ImageDto(
                image.getSource()
        );
    }

    public AnimeBannerImageDto fromBanner(AnimeBannerImage bannerImage) {
        var image = fromImage(bannerImage.getImage());
        return new AnimeBannerImageDto(bannerImage.getColor(), image);
    }
}
