package com.animusic.core.db.utils;

import java.util.Date;

import com.animusic.core.db.model.Image;
import com.animusic.core.db.model.User;

public interface ContentSubscription {

    Integer id();

    String name();

    User user();

    Date addedAt();

    SubscriptionTargetType targetType();

    Image image();

    String parentName();

}
