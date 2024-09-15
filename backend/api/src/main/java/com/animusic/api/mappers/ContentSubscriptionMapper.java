package com.animusic.api.mappers;

import java.util.List;

import com.animusic.api.dto.ContentSubscriptionDto;
import com.animusic.core.db.views.ContentSubscription;
import org.springframework.stereotype.Component;

@Component
public class ContentSubscriptionMapper {

    public static List<ContentSubscriptionDto> fromSubscriptions(List<ContentSubscription> subscriptions) {
        return subscriptions.stream().map(s -> {
            return new ContentSubscriptionDto(
                    s.getId(),
                    s.getName(),
                    s.getAddedAt(),
                    s.getTargetType(),
                    ImageMapper.fromImage(s.getImage()),
                    s.getParentName()
            );
        }).toList();
    }
}
