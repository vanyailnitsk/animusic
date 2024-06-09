package com.animusic.soundtrack.dao;

import com.animusic.album.dao.Album;
import com.animusic.anime.dao.Anime;
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
public class Soundtrack {
    @Id
    @GeneratedValue
    private Integer id;
    private String originalTitle;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;
    private String animeTitle;
    private String audioFile;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;
    private Integer duration;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    public Soundtrack(String originalTitle, String animeTitle) {
        this.originalTitle = originalTitle;
        this.animeTitle = animeTitle;
    }
}
