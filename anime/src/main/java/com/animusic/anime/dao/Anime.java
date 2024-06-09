package com.animusic.anime.dao;

import com.animusic.image.dao.AnimeBannerImage;
import com.animusic.image.dao.Image;
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

    public Anime(String title, String studio, String description, String folderName) {
        this.title = title;
        this.studio = studio;
        this.description = description;
        this.folderName = folderName;
    }

}

