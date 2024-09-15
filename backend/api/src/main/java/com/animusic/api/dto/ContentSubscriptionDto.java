package com.animusic.api.dto;

import java.util.Date;

import com.animusic.core.db.utils.SubscriptionTargetType;

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
