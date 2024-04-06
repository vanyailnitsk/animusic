package com.ilnitsk.animusic.album.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ilnitsk.animusic.anime.dao.Anime;
import com.ilnitsk.animusic.image.dao.CoverArt;
import com.ilnitsk.animusic.soundtrack.dao.Soundtrack;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "album")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "anime_id")
    @JsonIgnore
    private Anime anime;
    @OneToOne
    @JoinColumn(name = "cover_art_id")
    private CoverArt coverArt;
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Soundtrack> soundtracks = new ArrayList<>();
    public void addSoundtrack(Soundtrack soundtrack) {
        soundtracks.add(soundtrack);
    }

}
