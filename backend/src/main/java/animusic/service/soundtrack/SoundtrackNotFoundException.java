package animusic.service.soundtrack;

import animusic.api.exception.NotFoundException;

public class SoundtrackNotFoundException extends NotFoundException {
    public SoundtrackNotFoundException(Integer id) {
        super("Soundtrack with id " + id + " not found");
    }
}
