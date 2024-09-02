package com.animusic.core.db.model;

import java.util.Date;

import jakarta.persistence.Entity;
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
import org.jetbrains.annotations.Nullable;

@Entity
@Table(name = "track_listening_events")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackListeningEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Soundtrack soundtrack;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date listenedAt;

}
