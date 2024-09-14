package com.animusic.content.subscription;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import com.animusic.core.db.model.SubscriptionForAlbum;
import com.animusic.core.db.model.SubscriptionForAnime;
import com.animusic.core.db.table.SubscriptionForAlbumRepository;
import com.animusic.core.db.table.SubscriptionForAnimeRepository;
import com.animusic.core.db.utils.ContentSubscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentSubscriptionService {

    private final SubscriptionForAnimeRepository subscriptionForAnimeRepository;

    private final SubscriptionForAlbumRepository subscriptionForAlbumRepository;

    public List<? extends ContentSubscription> findUserSubscriptions(Integer userId) {
        var animeSubscriptions = findSubscriptionsForAnime(userId);
        var albumSubscriptions = findSubscriptionsForAlbum(userId);

        return Stream.of(albumSubscriptions, animeSubscriptions)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(ContentSubscription::addedAt).reversed())
                .toList();
    }

    public List<SubscriptionForAnime> findSubscriptionsForAnime(Integer userId) {
        return subscriptionForAnimeRepository.findByUserId(userId);
    }

    public List<SubscriptionForAlbum> findSubscriptionsForAlbum(Integer userId) {
        return subscriptionForAlbumRepository.findByUserId(userId);
    }

}
