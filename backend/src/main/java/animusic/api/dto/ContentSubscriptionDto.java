package animusic.api.dto;

import java.util.Date;

import animusic.core.db.utils.SubscriptionTargetType;


public record ContentSubscriptionDto(
        Integer id,

        Integer entityId,

        String name,

        Date addedAt,

        SubscriptionTargetType targetType,

        ImageDto image,

        String parentName
) {

}
