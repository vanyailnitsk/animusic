package com.animusic.content.anime;

import com.animusic.content.core.NotFoundException;

public class AnimeNotFoundException extends NotFoundException {
    public AnimeNotFoundException(Integer id) {
        super("Anime with id " + id + " not found");
    }
}
