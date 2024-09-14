package com.animusic.core.db.utils;

import java.util.Date;

import com.animusic.core.db.model.User;

public interface ContentSubscription {

    Integer id();

    User user();

    Date addedAt();

    SubscriptionTargetType targetType();

}
