package com.animusic.api.mappers;

import com.animusic.api.dto.AnimeBannerImageDto;
import com.animusic.api.dto.ImageDto;
import com.animusic.core.db.model.AnimeBannerImage;
import com.animusic.core.db.model.Image;
import com.animusic.s3.StoragePathResolver;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    @Nullable
    public static ImageDto fromImage(@Nullable Image image) {
        if (image == null) {
            return null;
        }
        return new ImageDto(
                StoragePathResolver.getAbsoluteFileUrl(image.getSource())
        );
    }

    public static AnimeBannerImageDto fromAnimeBanner(AnimeBannerImage bannerImage) {
        if (bannerImage == null) {
            return null;
        }
        var image = fromImage(bannerImage.getImage());
        return new AnimeBannerImageDto(bannerImage.getColor(), image);
    }
}
