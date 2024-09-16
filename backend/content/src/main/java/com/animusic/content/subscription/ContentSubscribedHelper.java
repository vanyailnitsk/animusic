package com.animusic.content.subscription;

import java.util.Optional;

import com.animusic.core.db.model.SubscriptionForAlbum;
import com.animusic.core.db.model.User;
import com.animusic.user.service.UserService;
import org.springframework.stereotype.Service;

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
                .peek(System.out::println)
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

        return albumSubscriptions.map(SubscriptionForAlbum::getId)
                .anyMatch(s -> s.equals(albumId));
    }
}
