package com.ilnitsk.animusic.anime;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ilnitsk.animusic.playlist.Playlist;
import com.ilnitsk.animusic.soundtrack.Soundtrack;
import jakarta.persistence.*;
import lombok.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
@ToString
public class Anime {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String title;
    private String studio;
    private Year releaseYear;
    private String description;
    @Column(unique = true)
    private String folderName;
    @Column(unique=true)
    private String bannerImagePath;
    @Column(unique=true)
    private String cardImagePath;
    @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Soundtrack> soundtracks = new ArrayList<>();
    @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Playlist> playlists = new ArrayList<>();

    public Anime(String title, String studio, Year releaseYear, String description, String folderName) {
        this.title = title;
        this.studio = studio;
        this.releaseYear = releaseYear;
        this.description = description;
        this.folderName = folderName;
    }

}

