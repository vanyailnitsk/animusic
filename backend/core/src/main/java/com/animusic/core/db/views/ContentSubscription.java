package com.animusic.core.db.views;

import java.util.Date;

import com.animusic.core.db.model.Image;
import com.animusic.core.db.model.User;
import com.animusic.core.db.utils.SubscriptionTargetType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ContentSubscription {

    Integer id;

    Integer entityId;

    String name;

    User user;

    Date addedAt;

    SubscriptionTargetType targetType;

    Image image;

    String parentName;

}
