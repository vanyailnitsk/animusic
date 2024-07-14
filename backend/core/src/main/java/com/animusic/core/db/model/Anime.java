package com.animusic.core.db.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "anime")
@Builder
@Value
@RequiredArgsConstructor
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

    @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Soundtrack> soundtracks = new ArrayList<>();

    @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Album> albums = new ArrayList<>();

    public Anime() {

    }
}

