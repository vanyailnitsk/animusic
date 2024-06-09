package com.animusic.api.dto;

import com.animusic.image.dao.CoverArt;
import lombok.AllArgsConstructor;

public record CoverArtDto(
        Colors colors,
        ImageDto image
) {
    @AllArgsConstructor
    static class Colors {
        private String colorLight;
        private String colorDark;
    }

    public static CoverArtDto fromCoverArt(CoverArt coverArt) {
        return new CoverArtDto(
                new Colors(coverArt.getColorLight(),coverArt.getColorDark()),
                ImageDto.fromImage(coverArt.getImage())
        );
    }
}
