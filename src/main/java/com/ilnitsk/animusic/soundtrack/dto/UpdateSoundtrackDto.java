package com.ilnitsk.animusic.soundtrack.dto;

public record UpdateSoundtrackDto(
        String originalTitle,
        String animeTitle,
        Integer duration
) {
}
