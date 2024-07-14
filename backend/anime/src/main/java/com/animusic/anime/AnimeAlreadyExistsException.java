package com.animusic.anime;

public class AnimeAlreadyExistsException extends RuntimeException {
    public AnimeAlreadyExistsException(String animeTitle) {
        super("Anime title={%s} already exists".formatted(animeTitle));
    }
}
