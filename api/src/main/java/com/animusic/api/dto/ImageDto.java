package com.animusic.api.dto;

import com.animusic.image.dao.Image;

public record ImageDto(
        String source
) {
    public static ImageDto fromImage(Image image) {
        return new ImageDto(
                image.getSource()
        );
    }
}
