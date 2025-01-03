package animusic.api.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import animusic.api.dto.ContentSubscriptionDto;
import animusic.core.db.views.ContentSubscription;

@Component
public class ContentSubscriptionMapper {

    public static List<ContentSubscriptionDto> fromSubscriptions(List<ContentSubscription> subscriptions) {
        return subscriptions.stream().map(s -> {
            return new ContentSubscriptionDto(
                    s.getId(),
                    s.getEntityId(),
                    s.getName(),
                    s.getAddedAt(),
                    s.getTargetType(),
                    ImageMapper.fromImage(s.getImage()),
                    s.getParentName()
            );
        }).toList();
    }
}
