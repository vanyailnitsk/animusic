package com.ilnitsk.animusic.soundtrack.dto;

import lombok.Data;

@Data
public class UpdateSoundtrackDto {
    private String originalTitle;
    private String animeTitle;
    private Integer duration;
}
