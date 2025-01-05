package animusic.service.subscription;

import org.springframework.stereotype.Service;

import animusic.core.db.model.User;

@Service
public class ContentSubscribedHelper {

    public static Boolean isSubscribedToAnime(
            User user,
            ContentSubscriptionService subscriptionService,
            Integer animeId
    ) {
        if (user == null) {
            return false;
        }

        var animeSubscriptions = subscriptionService.findSubscriptionsForAnime(user.getId()).stream();

        return animeSubscriptions.map(subscription -> subscription.getAnime().getId())
                .anyMatch(s -> s.equals(animeId));
    }

    public static Boolean isSubscribedToAlbum(
            User user,
            ContentSubscriptionService subscriptionService,
            Integer albumId
    ) {
        if (user == null) {
            return false;
        }

        var albumSubscriptions = subscriptionService.findSubscriptionsForAlbum(user.getId()).stream();

        return albumSubscriptions.map(subscription -> subscription.getAlbum().getId())
                .anyMatch(s -> s.equals(albumId));
    }
}
