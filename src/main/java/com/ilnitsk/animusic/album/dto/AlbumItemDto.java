package com.ilnitsk.animusic.album.dto;

import com.ilnitsk.animusic.image.dto.CoverArtDto;
import lombok.Data;

@Data
public class AlbumItemDto {
    private Integer id;
    private String name;
    private CoverArtDto coverArt;
}
