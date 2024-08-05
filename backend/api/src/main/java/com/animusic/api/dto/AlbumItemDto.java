package com.animusic.api.dto;

public record AlbumItemDto(
        Integer id,
        String name,
        CoverArtDto coverArt
) {
}
