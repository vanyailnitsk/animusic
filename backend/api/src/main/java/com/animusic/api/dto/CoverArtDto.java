package com.animusic.api.dto;

import com.animusic.core.db.model.CoverArt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CoverArtDto {
    Colors colors;

    ImageDto image;

    @AllArgsConstructor
    static class Colors {
        private String colorLight;
        private String colorDark;
    }

    public static CoverArtDto fromCoverArt(CoverArt coverArt) {
        return CoverArtDto.builder()
                .colors(new Colors(coverArt.getColorLight(), coverArt.getColorDark()))
                .image(ImageDto.fromImage(coverArt.getImage()))
                .build();
    }
}
