package com.animusic.content.soundtrack;

import com.animusic.content.core.NotFoundException;

public class SoundtrackNotFoundException extends NotFoundException {
    public SoundtrackNotFoundException(Integer id) {
        super("Soundtrack with id " + id + " not found");
    }
}
