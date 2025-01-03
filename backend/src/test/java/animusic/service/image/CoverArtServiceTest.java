package animusic.service.image;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import animusic.service.IntegrationTestBase;

@Transactional
class CoverArtServiceTest extends IntegrationTestBase {

    @Test
    void createAlbumCoverArt() {
    }

    @Test
    void createPlaylistCoverArt() {
    }

    @Test
    void generateHash() {
        System.out.println(CoverArtService.generateHash("Favourite tracks", 10));
        System.out.println(CoverArtService.generateHash("Favourite tracks", 9));
    }
}