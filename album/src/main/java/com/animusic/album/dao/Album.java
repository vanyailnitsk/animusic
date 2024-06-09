package com.animusic.album.dao;

import com.animusic.anime.dao.Anime;
import com.animusic.image.dao.CoverArt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "anime_id")
    @JsonIgnore
    private Anime anime;
    @OneToOne
    @JoinColumn(name = "cover_art_id")
    private CoverArt coverArt;

}
