package com.ilnitsk.animusic.playlist.dto;

import lombok.Data;

@Data
public class UpdatePlaylistDto {
    private Integer animeId;
    private String name;
    private String imageUrl;
}
