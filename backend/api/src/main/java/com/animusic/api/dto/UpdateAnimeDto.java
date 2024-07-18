package com.animusic.api.dto;

public record UpdateAnimeDto(
        String title,
        String studio,
        Integer releaseYear,
        String description,
        String folderName
) {
}
