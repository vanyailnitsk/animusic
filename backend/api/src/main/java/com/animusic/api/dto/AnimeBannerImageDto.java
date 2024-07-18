package com.animusic.api.dto;

import com.animusic.core.db.model.AnimeBannerImage;
import lombok.NonNull;

public record AnimeBannerImageDto(
        String color,
        ImageDto image
) {
    public static AnimeBannerImageDto fromAnimeBannerImage(@NonNull AnimeBannerImage animeBannerImage) {
        return new AnimeBannerImageDto(
                animeBannerImage.getColor(),
                ImageDto.fromImage(animeBannerImage.getImage())
        );
    }
}
