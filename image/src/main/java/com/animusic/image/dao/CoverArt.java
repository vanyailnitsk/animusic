package com.animusic.image.dao;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class CoverArt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "color_light")
    private String colorLight;
    @Column(name = "color_dark")
    private String colorDark;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;
}
