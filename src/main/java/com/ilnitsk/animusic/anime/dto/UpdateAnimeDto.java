package com.ilnitsk.animusic.anime.dto;

import lombok.Data;

import java.time.Year;

@Data
public class UpdateAnimeDto {
    private String title;
    private String studio;
    private Year releaseYear;
    private String description;
    private String folderName;
    private String bannerImagePath;
    private String cardImagePath;
}
