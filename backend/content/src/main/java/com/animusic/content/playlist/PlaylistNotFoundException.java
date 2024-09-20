package com.animusic.content.playlist;

import com.animusic.content.core.NotFoundException;

public class PlaylistNotFoundException extends NotFoundException {
    public PlaylistNotFoundException(Integer id) {
        super("Playlist with id " + id + " not found");
    }
}
