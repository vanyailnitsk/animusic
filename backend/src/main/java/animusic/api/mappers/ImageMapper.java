package animusic.api.mappers;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import animusic.api.dto.AnimeBannerImageDto;
import animusic.api.dto.ImageDto;
import animusic.core.db.model.AnimeBannerImage;
import animusic.core.db.model.Image;
import animusic.util.StoragePathResolver;

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
