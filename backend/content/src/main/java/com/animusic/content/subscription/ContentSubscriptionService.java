package com.animusic.content.subscription;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import com.animusic.core.db.model.Album;
import com.animusic.core.db.model.Anime;
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
                .map(s -> {
                    Anime anime = s.getAnime();
                    return ContentSubscription.builder()
                            .id(s.getId())
                            .entityId(anime.getId())
                            .name(anime.getTitle())
                            .user(s.getUser())
                            .addedAt(s.getAddedAt())
                            .targetType(ANIME)
                            .image(anime.getCardImage())
                            .parentName("")
                            .build();
                }).toList();

        List<ContentSubscription> dtosForAlbum = albumSubscriptions.stream()
                .map(s -> {
                    Album album = s.getAlbum();
                    return ContentSubscription.builder()
                            .id(s.getId())
                            .entityId(album.getId())
                            .name(album.getName())
                            .user(s.getUser())
                            .addedAt(s.getAddedAt())
                            .targetType(ALBUM)
                            .image(s.getImage())
                            .parentName(album.getAnime().getTitle())
                            .build();
                }).toList();

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
