package com.animusic.content.subscription;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import com.animusic.core.db.model.SubscriptionForAlbum;
import com.animusic.core.db.model.SubscriptionForAnime;
import com.animusic.core.db.table.SubscriptionForAlbumRepository;
import com.animusic.core.db.table.SubscriptionForAnimeRepository;
import com.animusic.core.db.views.ContentSubscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.animusic.core.db.utils.SubscriptionTargetType.ALBUM;
import static com.animusic.core.db.utils.SubscriptionTargetType.ANIME;

@Service
@RequiredArgsConstructor
public class ContentSubscriptionService {

    private final SubscriptionForAnimeRepository subscriptionForAnimeRepository;

    private final SubscriptionForAlbumRepository subscriptionForAlbumRepository;

    public List<ContentSubscription> findUserSubscriptions(Integer userId, Integer limit) {
        var animeSubscriptions = findSubscriptionsForAnime(userId);
        var albumSubscriptions = findSubscriptionsForAlbum(userId);

        List<ContentSubscription> dtosForAnime = animeSubscriptions.stream()
                .map(s -> ContentSubscription.builder()
                        .id(s.getId())
                        .name(s.getAnime().getTitle())
                        .user(s.getUser())
                        .addedAt(s.getAddedAt())
                        .targetType(ANIME)
                        .image(s.getAnime().getCardImage())
                        .parentName("")
                        .build()).toList();

        List<ContentSubscription> dtosForAlbum = albumSubscriptions.stream()
                .map(s -> ContentSubscription.builder()
                        .id(s.getId())
                        .name(s.getAlbum().getName())
                        .user(s.getUser())
                        .addedAt(s.getAddedAt())
                        .targetType(ALBUM)
                        .image(s.getImage())
                        .parentName(s.getAlbum().getAnime().getTitle())
                        .build()).toList();

        return Stream.of(dtosForAnime, dtosForAlbum)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(ContentSubscription::getAddedAt).reversed())
                .limit(limit)
                .toList();
    }

    public List<SubscriptionForAnime> findSubscriptionsForAnime(Integer userId) {
        return subscriptionForAnimeRepository.findByUserId(userId);
    }

    public List<SubscriptionForAlbum> findSubscriptionsForAlbum(Integer userId) {
        return subscriptionForAlbumRepository.findByUserId(userId);
    }

}
