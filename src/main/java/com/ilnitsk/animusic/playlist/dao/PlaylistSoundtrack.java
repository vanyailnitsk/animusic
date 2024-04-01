package com.ilnitsk.animusic.playlist.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ilnitsk.animusic.soundtrack.Soundtrack;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "playlist_soundtrack")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PlaylistSoundtrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    @JsonBackReference
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "soundtrack_id")
    private Soundtrack soundtrack;

    @Column(name = "added_at")
    private LocalDateTime addedAt;
}
