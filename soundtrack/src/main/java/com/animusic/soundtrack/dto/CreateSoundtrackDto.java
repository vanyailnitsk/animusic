package com.animusic.soundtrack.dto;

public record CreateSoundtrackDto(
        String originalTitle,
        String animeTitle,
        Integer duration
) {
}
