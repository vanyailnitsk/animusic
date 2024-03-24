package com.ilnitsk.animusic.soundtrack.dto;

import lombok.Data;

@Data
public class SoundtrackEntityDto {
    private Integer id;
    private String originalTitle;
    private String animeTitle;
    private String audioFile;
    private String imageFile;
    private Integer duration;
}
