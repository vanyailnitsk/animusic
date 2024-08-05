package com.animusic.api.dto;

import lombok.Value;

@Value
public class PlaylistOwnerDto {
    Integer id;
    String name;
    Avatar avatar;

    public record Avatar(String url) {
    }
}
