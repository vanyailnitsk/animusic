package com.animusic.content.soundtrack;

public class SoundtrackNotFoundException extends RuntimeException {
    public SoundtrackNotFoundException(Integer id) {
        super("Soundtrack with id " + id + " not found");
    }
}
