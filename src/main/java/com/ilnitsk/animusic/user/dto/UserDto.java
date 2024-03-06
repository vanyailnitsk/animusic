package com.ilnitsk.animusic.user.dto;

import com.ilnitsk.animusic.user.User;
import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
