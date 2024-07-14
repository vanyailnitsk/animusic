package com.animusic.album;

public class AlbumNotFoundException extends RuntimeException{
    public AlbumNotFoundException(Integer id) {
        super("Album with id "+id+" not found");
    }
}
