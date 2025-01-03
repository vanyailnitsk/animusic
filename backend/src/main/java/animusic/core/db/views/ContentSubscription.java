package animusic.core.db.views;

import java.util.Date;

import lombok.Builder;
import lombok.Value;

import animusic.core.db.model.Image;
import animusic.core.db.model.User;
import animusic.core.db.utils.SubscriptionTargetType;

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
