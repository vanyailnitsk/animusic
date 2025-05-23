package com.animusic.content.subscription;

import java.util.Date;
import java.util.Optional;

import com.animusic.core.db.model.Album;
import com.animusic.core.db.model.Anime;
import com.animusic.core.db.model.SubscriptionForAlbum;
import com.animusic.core.db.model.SubscriptionForAnime;
import com.animusic.core.db.model.User;
import com.animusic.core.db.table.SubscriptionForAlbumRepository;
import com.animusic.core.db.table.SubscriptionForAnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentSubscriptionsManager {

    private final SubscriptionForAnimeRepository subscriptionForAnimeRepository;

    private final SubscriptionForAlbumRepository subscriptionForAlbumRepository;

    @Transactional
    public void subscribeToAnime(User user, Anime anime) {
        var subscription = SubscriptionForAnime.builder()
                .user(user)
                .anime(anime)
                .addedAt(new Date())
                .build();
        if (!subscriptionForAnimeRepository.alreadySubscribed(user.getId(), anime.getId())) {
            subscriptionForAnimeRepository.save(subscription);
        }
    }

    @Transactional
    public void subscribeToAlbum(User user, Album album) {
        var subscription = SubscriptionForAlbum.builder()
                .user(user)
                .album(album)
                .addedAt(new Date())
                .build();
        if (!subscriptionForAlbumRepository.alreadySubscribed(user.getId(), album.getId())) {
            subscriptionForAlbumRepository.save(subscription);
        }
    }

    @Transactional
    public void unsubscribeFromAnime(User user, Anime anime) {
        Optional<SubscriptionForAnime> entity = subscriptionForAnimeRepository.findAnimeSubscription(
                user.getId(), anime.getId()
        );

        entity.ifPresent(subscriptionForAnimeRepository::delete);
    }

    @Transactional
    public void unsubscribeFromAlbum(User user, Album album) {
        Optional<SubscriptionForAlbum> entity = subscriptionForAlbumRepository.findAlbumSubscription(
                user.getId(), album.getId()
        );

        entity.ifPresent(subscriptionForAlbumRepository::delete);
    }
}
