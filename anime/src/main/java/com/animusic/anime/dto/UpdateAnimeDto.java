package com.animusic.anime.dto;

public record UpdateAnimeDto(
        String title,
        String studio,
        Integer releaseYear,
        String description,
        String folderName
) {
}
