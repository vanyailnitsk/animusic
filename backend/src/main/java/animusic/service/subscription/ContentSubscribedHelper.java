package animusic.service.subscription;

import java.util.Optional;

import org.springframework.stereotype.Service;

import animusic.core.db.model.User;
import animusic.service.security.UserService;

@Service
public class ContentSubscribedHelper {

    public static Boolean isSubscribedToAnime(
            UserService userService,
            ContentSubscriptionService subscriptionService,
            Integer animeId
    ) {
        Optional<User> user = userService.getUserInSession();
        if (user.isEmpty()) {
            return false;
        }

        var animeSubscriptions = subscriptionService.findSubscriptionsForAnime(user.get().getId()).stream();

        return animeSubscriptions.map(subscription -> subscription.getAnime().getId())
                .anyMatch(s -> s.equals(animeId));
    }

    public static Boolean isSubscribedToAlbum(
            UserService userService,
            ContentSubscriptionService subscriptionService,
            Integer albumId
    ) {
        Optional<User> user = userService.getUserInSession();
        if (user.isEmpty()) {
            return false;
        }

        var albumSubscriptions = subscriptionService.findSubscriptionsForAlbum(user.get().getId()).stream();

        return albumSubscriptions.map(subscription -> subscription.getAlbum().getId())
                .anyMatch(s -> s.equals(albumId));
    }
}
