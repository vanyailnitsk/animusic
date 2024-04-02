package com.ilnitsk.animusic.exception;

public class AlbumNotFoundException extends RuntimeException{
    public AlbumNotFoundException(Integer id) {
        super("Playlist with id "+id+" not found");
    }
}
