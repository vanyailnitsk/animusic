package com.animusic.image.dao;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "anime_banner")
@Data
public class AnimeBannerImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "color",length = 7)
    private String color;
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
}
