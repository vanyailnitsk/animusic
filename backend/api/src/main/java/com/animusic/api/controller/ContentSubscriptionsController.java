package com.animusic.api.controller;

import java.util.List;

import com.animusic.api.dto.ContentSubscriptionDto;
import com.animusic.api.mappers.ContentSubscriptionMapper;
import com.animusic.content.album.AlbumService;
import com.animusic.content.anime.AnimeNotFoundException;
import com.animusic.content.anime.AnimeService;
import com.animusic.content.subscription.ContentSubscriptionService;
import com.animusic.content.subscription.ContentSubscriptionsManager;
import com.animusic.core.db.model.User;
import com.animusic.core.db.views.ContentSubscription;
import com.animusic.user.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.animusic.api.controller.ContentSubscriptionsController.SubscribeOperationName.SUBSCRIBE;
import static com.animusic.api.controller.ContentSubscriptionsController.SubscribeOperationName.UNSUBSCRIBE;

@RestController
@RequestMapping("/api/subscriptions")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Subscriptions to anime / album")
public class ContentSubscriptionsController {

    private final ContentSubscriptionService contentSubscriptionService;

    private final ContentSubscriptionsManager contentSubscriptionsManager;

    private final UserService userService;

    private final AnimeService animeService;

    private final AlbumService albumService;

    @GetMapping("library")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<ContentSubscriptionDto> fetchSubscriptions(
            @RequestParam(required = false, defaultValue = "10")
            @Schema(description = "Ограничение по количеству", defaultValue = "10") Integer limit
    ) {
        User user = userService.getUserInSession().get();

        List<ContentSubscription> subscriptions = contentSubscriptionService.findUserSubscriptions(user.getId(), limit);

        return ContentSubscriptionMapper.fromSubscriptions(subscriptions);
    }

    @PostMapping("/anime/{animeId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void subscribeToAnime(
            @PathVariable("animeId") Integer animeId,
            @RequestParam
            @Schema(description = "Operation", allowableValues = {"SUBSCRIBE", "UNSUBSCRIBE"})
            SubscribeOperationName operation
    ) {
        User user = userService.getUserInSession().get();

        var anime = animeService.getAnime(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));

        if (operation == SUBSCRIBE) {
            contentSubscriptionsManager.subscribeToAnime(user, anime);
        } else if (operation == UNSUBSCRIBE) {
            contentSubscriptionsManager.unsubscribeFromAnime(user, anime);
        }
    }

    @PostMapping("/album/{albumId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void subscribeToAlbum(
            @PathVariable("albumId") Integer albumId,
            @RequestParam
            @Schema(description = "Operation", allowableValues = {"SUBSCRIBE", "UNSUBSCRIBE"})
            SubscribeOperationName operation
    ) {
        User user = userService.getUserInSession().get();

        var album = albumService.getAlbumById(albumId);

        if (operation == SUBSCRIBE) {
            contentSubscriptionsManager.subscribeToAlbum(user, album);
        } else if (operation == UNSUBSCRIBE) {
            contentSubscriptionsManager.unsubscribeFromAlbum(user, album);
        }

    }

    enum SubscribeOperationName {
        SUBSCRIBE,
        UNSUBSCRIBE
    }

}
