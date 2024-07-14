package com.animusic.core.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "playlist")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<PlaylistSoundtrack> soundtracks;
    @OneToOne
    @JoinColumn(name = "cover_art_id")
    private CoverArt coverArt;
}
