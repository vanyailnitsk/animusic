package com.ilnitsk.animusic.user.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user_playlist")
@RequiredArgsConstructor
@Getter
@Setter
public class UserPlaylist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<UserPlaylistSoundtrack> soundtracks;
}
