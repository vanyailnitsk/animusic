package com.ilnitsk.animusic.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "anime_id")
    @JsonIgnore
    private Anime anime;
    @ManyToMany
    @JoinTable(
            name = "playlist_soundtrack",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "soundtrack_id")
    )
    @JsonManagedReference
    private List<Soundtrack> soundtracks = new ArrayList<>();
}
