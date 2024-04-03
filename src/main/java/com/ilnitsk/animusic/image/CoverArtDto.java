package com.ilnitsk.animusic.image;

import lombok.Data;

@Data
public class CoverArtDto {
    private Colors colors;
    private String imageUrl;

    public CoverArtDto() {
        colors = new Colors();
    }

    public void setColorDark(String colorDark) {
        colors.setColorDark(colorDark);
    }

    public void setColorLight(String colorLight) {
        colors.setColorDark(colorLight);
    }

    @Data
    class Colors {
        private String colorLight;
        private String colorDark;

    }
}
