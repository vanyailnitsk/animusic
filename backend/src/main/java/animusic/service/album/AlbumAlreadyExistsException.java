package animusic.service.album;

public class AlbumAlreadyExistsException extends RuntimeException {
    public AlbumAlreadyExistsException(String albumName, Integer animeId) {
        super("Album {%s} in anime {%d} already exists".formatted(albumName, animeId));
    }
}
