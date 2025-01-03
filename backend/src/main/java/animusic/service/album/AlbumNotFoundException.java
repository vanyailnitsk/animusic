package animusic.service.album;

import animusic.api.exception.NotFoundException;

public class AlbumNotFoundException extends NotFoundException {
    public AlbumNotFoundException(Integer id) {
        super("Album with id " + id + " not found");
    }

}
