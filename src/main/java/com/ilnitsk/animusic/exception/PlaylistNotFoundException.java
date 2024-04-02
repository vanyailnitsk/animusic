package com.ilnitsk.animusic.exception;

public class PlaylistNotFoundException extends RuntimeException {
    public PlaylistNotFoundException(Long id) {
        super("Playlist with id "+id+" not found");
    }
}
