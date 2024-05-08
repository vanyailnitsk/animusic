package com.ilnitsk.animusic.album.dto;

import com.ilnitsk.animusic.image.dto.CoverArtDto;

public record AlbumItemDto (
    Integer id,
    String name,
    CoverArtDto coverArt
){}
