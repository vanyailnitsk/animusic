package com.ilnitsk.animusic.anime.dto;

import lombok.Data;

@Data
public class UpdateAnimeDto {
    private String title;
    private String studio;
    private Integer releaseYear;
    private String description;
    private String folderName;
}
