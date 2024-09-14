package com.animusic.content.subscription;

import java.util.Date;
import java.util.Optional;

import com.animusic.content.IntegrationTestBase;
import com.animusic.core.db.model.Album;
import com.animusic.core.db.model.Anime;
import com.animusic.core.db.model.SubscriptionForAlbum;
import com.animusic.core.db.model.SubscriptionForAnime;
import com.animusic.core.db.model.User;
import com.animusic.core.db.table.SubscriptionForAlbumRepository;
import com.animusic.core.db.table.SubscriptionForAnimeRepository;
import com.animusic.core.db.utils.SubscriptionTargetType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

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
                .title("Anime-1")
                .build();

        var subscription = new SubscriptionForAnime(1, anime, user, new Date());
        when(subscriptionForAnimeRepository.findById(1)).thenReturn(Optional.of(subscription));

        contentSubscriptionsManager.unsubscribeFromContent(user, 1, SubscriptionTargetType.ANIME);

        verify(subscriptionForAnimeRepository).findById(1);
        verify(subscriptionForAnimeRepository).deleteById(1);
    }

    @Test
    void unsubscribeFromAlbum() {
        var user = User.builder()
                .id(1)
                .username("user")
                .build();

        var album = Album.builder()
                .name("Album-1")
                .build();

        var subscription = new SubscriptionForAlbum(1, album, user, new Date());
        when(subscriptionForAlbumRepository.findById(1)).thenReturn(Optional.of(subscription));

        contentSubscriptionsManager.unsubscribeFromContent(user, 1, SubscriptionTargetType.ANIME);

        verify(subscriptionForAlbumRepository).findById(1);
        verify(subscriptionForAlbumRepository).deleteById(1);
    }
}