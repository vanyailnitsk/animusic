package animusic.service.anime;

import animusic.api.exception.NotFoundException;

public class AnimeNotFoundException extends NotFoundException {
    public AnimeNotFoundException(Integer id) {
        super("Anime with id " + id + " not found");
    }
}
