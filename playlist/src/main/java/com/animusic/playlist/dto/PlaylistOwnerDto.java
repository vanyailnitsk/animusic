package com.animusic.playlist.dto;

import lombok.Data;

@Data
public class PlaylistOwnerDto {
    private Integer id;
    private String name;
    private Avatar avatar;

    public PlaylistOwnerDto() {
        avatar = new Avatar();
    }

    @Data
    class Avatar {
        private String url = "pain-avatar.jpeg";

        public String getUrl() {
            return url;
        }
    }
}
