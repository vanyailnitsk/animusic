package com.animusic.api.controller;

import java.util.List;

import com.animusic.api.dto.ContentSubscriptionDto;
import com.animusic.api.mappers.ContentSubscriptionMapper;
import com.animusic.content.subscription.ContentSubscriptionService;
import com.animusic.core.db.model.User;
import com.animusic.core.db.views.ContentSubscription;
import com.animusic.user.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriptions")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Subscriptions to anime / album")
public class ContentSubscriptionsController {

    private final ContentSubscriptionService contentSubscriptionService;

    private final UserService userService;

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
}
