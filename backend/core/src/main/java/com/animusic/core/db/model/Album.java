package com.animusic.core.db.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Entity
@Table(name = "album")
@Builder
@Data
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "anime_id")
    private Anime anime;

    @OneToOne
    @JoinColumn(name = "cover_art_id")
    private CoverArt coverArt;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @Singular
    private List<Soundtrack> soundtracks;
}
