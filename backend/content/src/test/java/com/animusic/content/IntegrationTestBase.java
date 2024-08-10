package com.animusic.content;

import com.animusic.content.conf.ContentConfig;
import com.animusic.core.conf.DatabaseConfig;
import com.animusic.core.db.table.AlbumRepository;
import com.animusic.core.db.table.AnimeBannerImageRepository;
import com.animusic.core.db.table.AnimeRepository;
import com.animusic.core.db.table.CoverArtRepository;
import com.animusic.core.db.table.ImageRepository;
import com.animusic.core.db.table.PlaylistRepository;
import com.animusic.core.db.table.PlaylistSoundtrackRepository;
import com.animusic.core.db.table.SoundtrackRepository;
import com.animusic.s3.S3Service;
import com.animusic.user.service.UserService;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;

@SpringJUnitConfig({
        DatabaseConfig.class,
        IntegrationTestsConfig.class,
        ContentConfig.class
})
@AutoConfigureEmbeddedDatabase(provider = ZONKY)
@Slf4j
public class IntegrationTestBase {

    @MockBean
    protected UserService userService;

    @Autowired
    protected S3ServiceTestImpl s3Service;

    @Autowired
    protected AlbumRepository albumRepository;

    @Autowired
    protected AnimeBannerImageRepository animeBannerImageRepository;

    @Autowired
    protected AnimeRepository animeRepository;

    @Autowired
    protected CoverArtRepository coverArtRepository;

    @Autowired
    protected ImageRepository imageRepository;

    @Autowired
    protected PlaylistRepository playlistRepository;

    @Autowired
    protected PlaylistSoundtrackRepository playlistSoundtrackRepository;

    @Autowired
    protected SoundtrackRepository soundtrackRepository;

    @Test
    void init() {
        log.info("{}",soundtrackRepository.findAll());
    }

}
