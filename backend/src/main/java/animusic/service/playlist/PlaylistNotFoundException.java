package animusic.service.playlist;

import animusic.api.exception.NotFoundException;

public class PlaylistNotFoundException extends NotFoundException {
    public PlaylistNotFoundException(Integer id) {
        super("Playlist with id " + id + " not found");
    }
}
