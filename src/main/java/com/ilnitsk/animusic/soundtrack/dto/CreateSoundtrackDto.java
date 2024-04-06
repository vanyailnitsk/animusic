package com.ilnitsk.animusic.soundtrack.dto;

import lombok.Data;

@Data
public class CreateSoundtrackDto {
    private String originalTitle;
    private String animeTitle;
    private Integer duration;
}
