package com.ilnitsk.animusic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimeNavDTO {
    private Integer id;
    private String title;

    public AnimeNavDTO(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

}

