package com.animusic.api.dto;

import com.animusic.anime.dao.Anime;

public record AnimeItemDto(
        Integer id,
        String title
) {
    public static AnimeItemDto fromAnime(Anime anime) {
        return new AnimeItemDto(anime.getId(),anime.getTitle());
    }
}
