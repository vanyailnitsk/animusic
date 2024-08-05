package com.animusic.api.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
public class CoverArtDto {
    Colors colors;

    ImageDto image;

    public CoverArtDto(ImageDto image, String colorLight, String colorDark) {
        this.image = image;
        this.colors = new Colors(colorLight, colorDark);
    }
    @AllArgsConstructor
    public static class Colors {
        private String colorLight;
        private String colorDark;
    }
}
