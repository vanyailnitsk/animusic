package com.ilnitsk.animusic.user.dao;

import com.ilnitsk.animusic.soundtrack.Soundtrack;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@RequiredArgsConstructor
@Getter
@Setter
public class UserPlaylistSoundtrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private UserPlaylist playlist;

    @ManyToOne
    @JoinColumn(name = "soundtrack_id")
    private Soundtrack soundtrack;

    @Column(name = "added_at")
    private LocalDateTime addedAt;
}
