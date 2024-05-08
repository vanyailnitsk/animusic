package com.ilnitsk.animusic.image.dto;

import lombok.Data;

public class CoverArtDto {
    private Colors colors;
    private ImageDto image;

    public CoverArtDto() {
        colors = new Colors();
    }

    public void setColorDark(String colorDark) {
        colors.setColorDark(colorDark);
    }

    public void setColorLight(String colorLight) {
        colors.setColorLight(colorLight);
    }

    @Data
    class Colors {
        private String colorLight;
        private String colorDark;

    }
}
