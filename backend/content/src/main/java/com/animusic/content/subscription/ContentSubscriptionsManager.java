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
import com.animusic.core.db.utils.SubscriptionTargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentSubscriptionsManager {

    private final SubscriptionForAnimeRepository subscriptionForAnimeRepository;

    private final SubscriptionForAlbumRepository subscriptionForAlbumRepository;

    @Transactional
    public SubscriptionForAnime subscribeToAnime(User user, Anime anime) {
        var subscription = SubscriptionForAnime.builder()
                .user(user)
                .anime(anime)
                .addedAt(new Date())
                .build();
        return subscriptionForAnimeRepository.save(subscription);
    }

    @Transactional
    public SubscriptionForAlbum subscribeToAlbum(User user, Album album) {
        var subscription = SubscriptionForAlbum.builder()
                .user(user)
                .album(album)
                .addedAt(new Date())
                .build();
        return subscriptionForAlbumRepository.save(subscription);
    }

    @Transactional
    public void unsubscribeFromContent(User user, Integer entityId, SubscriptionTargetType targetType) {
        // routing to correct table
        var table = switch (targetType) {
            case ANIME -> subscriptionForAnimeRepository;
            case ALBUM -> subscriptionForAlbumRepository;
        };

        var entity = table.findById(entityId).orElseThrow(
                () -> new NotFoundException("No subscription with id %d".formatted(entityId))
        );

        if (entity.user().getId().equals(user.getId())) {
            table.deleteById(entity.id());
        }
    }
}
