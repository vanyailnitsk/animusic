package com.animusic.content.anime;

public class AnimeNotFoundException extends RuntimeException {
    public AnimeNotFoundException(Integer id) {
        super("Anime with id " + id + " not found");
    }
}
