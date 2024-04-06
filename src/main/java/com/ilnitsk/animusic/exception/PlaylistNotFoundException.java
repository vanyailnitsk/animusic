package com.ilnitsk.animusic.exception;

public class PlaylistNotFoundException extends RuntimeException {
    public PlaylistNotFoundException(Integer id) {
        super("Playlist with id "+id+" not found");
    }
}
