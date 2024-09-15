package com.animusic.content.subscription;

import java.util.Date;

import com.animusic.content.core.NotFoundException;
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
    public void unsubscribeFromAnime(User user, Integer entityId) {
        SubscriptionForAnime entity = subscriptionForAnimeRepository.findById(entityId).orElseThrow(
                () -> new NotFoundException("No subscription with id %d".formatted(entityId))
        );

        if (entity.getUser().getId().equals(user.getId())) {
            subscriptionForAnimeRepository.delete(entity);
        }
    }

    @Transactional
    public void unsubscribeFromAlbum(User user, Integer entityId) {
        SubscriptionForAlbum entity = subscriptionForAlbumRepository.findById(entityId).orElseThrow(
                () -> new NotFoundException("No subscription with id %d".formatted(entityId))
        );

        if (entity.getUser().getId().equals(user.getId())) {
            subscriptionForAlbumRepository.delete(entity);
        }
    }
}
