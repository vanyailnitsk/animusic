package com.animusic.core.db.model;

import java.util.Date;

import com.animusic.core.db.utils.ContentSubscription;
import com.animusic.core.db.utils.SubscriptionTargetType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.animusic.core.db.utils.SubscriptionTargetType.ALBUM;

@Entity
@Table(name = "subscription_for_album")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionForAlbum implements ContentSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "added_at")
    private Date addedAt;

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public User user() {
        return user;
    }

    @Override
    public Date addedAt() {
        return addedAt;
    }

    @Override
    public SubscriptionTargetType targetType() {
        return ALBUM;
    }
}
