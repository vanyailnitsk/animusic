package com.ilnitsk.animusic.exception;

public class SoundtrackNotFoundException extends RuntimeException{
    public SoundtrackNotFoundException(Integer id) {
        super("Soundtrack with id "+id+" not found");
    }
}
