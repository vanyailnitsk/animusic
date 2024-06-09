package com.animusic.anime.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ilnitsk.animusic.album.dao.Album;
import com.ilnitsk.animusic.image.dao.AnimeBannerImage;
import com.ilnitsk.animusic.image.dao.Image;
import com.ilnitsk.animusic.soundtrack.dao.Soundtrack;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "release_year")
    private Integer releaseYear;
    private String description;
    @Column(unique = true)
    private String folderName;
    @OneToOne
    @JoinColumn(name = "banner_id")
    private AnimeBannerImage bannerImage;
    @OneToOne
    @JoinColumn(name = "card_image_id")
    private Image cardImage;
    @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Soundtrack> soundtracks = new ArrayList<>();
    @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Album> albums = new ArrayList<>();

    public Anime(String title, String studio, String description, String folderName) {
        this.title = title;
        this.studio = studio;
        this.description = description;
        this.folderName = folderName;
    }

}

