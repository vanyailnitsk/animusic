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
@ToString
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "anime_id")
    @JsonIgnore
    private Anime anime;
    @ManyToMany(targetEntity = Soundtrack.class)
    @JoinTable(name = "playlist_soundtrack",
            inverseJoinColumns = @JoinColumn(name = "soundtrack_id",
                    nullable = false,
                    updatable = false),
            joinColumns = @JoinColumn(name = "playlist_id",
                    nullable = false,
                    updatable = false),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @JsonManagedReference
    private List<Soundtrack> soundtracks = new ArrayList<>();
    public void addSoundtrack(Soundtrack soundtrack) {
        soundtracks.add(soundtrack);
    }
    public String getBannerLink() {
        return "/anime/banner/"+anime.getId();
    }

}
