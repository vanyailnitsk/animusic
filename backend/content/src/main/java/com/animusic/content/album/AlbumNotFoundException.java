package com.animusic.content.album;

import com.animusic.content.core.NotFoundException;

public class AlbumNotFoundException extends NotFoundException {
    public AlbumNotFoundException(Integer id) {
        super("Album with id " + id + " not found");
    }

}
