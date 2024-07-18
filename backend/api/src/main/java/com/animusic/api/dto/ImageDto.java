package com.animusic.api.dto;

import com.animusic.core.db.model.Image;
import org.jetbrains.annotations.Nullable;

public record ImageDto(
        String source
) {
    @Nullable
    public static ImageDto fromImage(Image image) {
        if (image == null) {
            return null;
        }
        return new ImageDto(
                image.getSource()
        );
    }
}
