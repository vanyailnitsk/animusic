package com.ilnitsk.animusic.anime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class AnimeNavDTO {
    private Integer id;
    private String title;

    public AnimeNavDTO(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

}

