package com.ilnitsk.animusic.user.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ilnitsk.animusic.soundtrack.Soundtrack;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserPlaylistSoundtrack {
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
