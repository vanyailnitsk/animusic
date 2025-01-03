package animusic.service;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import animusic.conf.ContentConfig;
import animusic.core.db.table.AlbumRepository;
import animusic.core.db.table.AnimeBannerImageRepository;
import animusic.core.db.table.AnimeRepository;
import animusic.core.db.table.CoverArtRepository;
import animusic.core.db.table.ImageRepository;
import animusic.core.db.table.PlaylistRepository;
import animusic.core.db.table.PlaylistSoundtrackRepository;
import animusic.core.db.table.SoundtrackRepository;
import animusic.core.db.table.SubscriptionForAlbumRepository;
import animusic.core.db.table.SubscriptionForAnimeRepository;
import animusic.core.db.table.TrackListeningEventRepository;
import animusic.core.db.table.UserRepository;
import animusic.service.security.UserService;

@SpringJUnitConfig({
        IntegrationTestsConfig.class,
        ContentConfig.class
})
@AutoConfigureEmbeddedDatabase
@Slf4j
public class IntegrationTestBase {

    @MockBean
    protected UserService userService;

    @SpyBean
    protected UserRepository userRepository;

    @Autowired
    protected S3ServiceTestImpl s3Service;

    @SpyBean
    protected AlbumRepository albumRepository;

    @Autowired
    protected AnimeBannerImageRepository animeBannerImageRepository;

    @SpyBean
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

    @Autowired
    protected TrackListeningEventRepository trackListeningEventRepository;

    @SpyBean
    protected SubscriptionForAlbumRepository subscriptionForAlbumRepository;

    @SpyBean
    protected SubscriptionForAnimeRepository subscriptionForAnimeRepository;

    @Test
    void init() {
        log.info("{}", soundtrackRepository.findAll());
    }

    @AfterEach
    void tearDown() {
        s3Service.clear();
    }
}
