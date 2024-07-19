package com.animusic.core.db.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "anime")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private List<Soundtrack> soundtracks;

    @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Album> albums;
}

