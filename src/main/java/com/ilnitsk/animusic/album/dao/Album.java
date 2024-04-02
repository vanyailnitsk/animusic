package com.ilnitsk.animusic.album.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ilnitsk.animusic.anime.Anime;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "anime_id")
    @JsonIgnore
    private Anime anime;
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Soundtrack> soundtracks = new ArrayList<>();
    public void addSoundtrack(Soundtrack soundtrack) {
        soundtracks.add(soundtrack);
    }
    public String getBannerLink() {
        return anime.getBannerImagePath();
    }

}
