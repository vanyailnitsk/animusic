package animusic.service.subscription;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import animusic.core.db.model.Album;
import animusic.core.db.model.Anime;
import animusic.core.db.model.SubscriptionForAlbum;
import animusic.core.db.model.SubscriptionForAnime;
import animusic.core.db.model.User;
import animusic.core.db.table.SubscriptionForAlbumRepository;
import animusic.core.db.table.SubscriptionForAnimeRepository;
import animusic.service.IntegrationTestBase;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Transactional
class ContentSubscriptionsManagerTest extends IntegrationTestBase {

    @MockBean
    SubscriptionForAnimeRepository subscriptionForAnimeRepository;

    @MockBean
    SubscriptionForAlbumRepository subscriptionForAlbumRepository;

    @Autowired
    ContentSubscriptionsManager contentSubscriptionsManager;

    @Test
    void unsubscribeFromAnime() {
        var user = User.builder()
                .id(1)
                .username("user")
                .build();

        var anime = Anime.builder()
                .id(2)
                .title("Anime-1")
                .build();

        var subscription = new SubscriptionForAnime(1, anime, user, new Date());
        when(subscriptionForAnimeRepository.findAnimeSubscription(user.getId(), anime.getId()))
                .thenReturn(Optional.of(subscription));

        contentSubscriptionsManager.unsubscribeFromAnime(user, anime);

        verify(subscriptionForAnimeRepository).findAnimeSubscription(1, 2);
        verify(subscriptionForAnimeRepository).delete(subscription);
    }

    @Test
    void unsubscribeFromAlbum() {
        var user = User.builder()
                .id(1)
                .username("user")
                .build();

        var album = Album.builder()
                .id(3)
                .name("Album-1")
                .build();

        var subscription = new SubscriptionForAlbum(1, album, user, new Date());
        when(subscriptionForAlbumRepository.findAlbumSubscription(user.getId(), album.getId()))
                .thenReturn(Optional.of(subscription));

        contentSubscriptionsManager.unsubscribeFromAlbum(user, album);

        verify(subscriptionForAlbumRepository).findAlbumSubscription(1, 3);
        verify(subscriptionForAlbumRepository).delete(subscription);
    }
}