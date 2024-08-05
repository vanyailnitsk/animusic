package com.animusic.api.dto;

public record CreateAnimeDto(
        String title,
        String studio,
        Integer releaseYear,
        String description,
        String folderName
) {
}
