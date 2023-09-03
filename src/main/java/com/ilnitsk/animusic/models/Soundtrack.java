package com.ilnitsk.animusic.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
@ToString
public class Soundtrack {
    @Id
    @GeneratedValue
    private Integer id;
    private String originalTitle;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "anime_id", nullable = false)
    @JsonBackReference
    private Anime anime;
    private String animeTitle;
    private TrackType type;
    @Column(unique = true)
    private String pathToFile;
    @JsonProperty("animeName")
    private String animeName;

    public Soundtrack(String originalTitle, String animeTitle, TrackType type) {
        this.originalTitle = originalTitle;
        this.animeTitle = animeTitle;
        this.type = type;
    }
}
