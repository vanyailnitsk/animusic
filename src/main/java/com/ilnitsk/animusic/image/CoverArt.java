package com.ilnitsk.animusic.image;

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
    @Column(name = "image_url")
    private String imageUrl;
}
