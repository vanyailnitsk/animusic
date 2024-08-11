package com.animusic.content.image;

import com.animusic.content.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

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