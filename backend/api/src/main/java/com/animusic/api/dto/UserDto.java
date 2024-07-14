package com.animusic.api.dto;

import java.util.Collection;

import com.animusic.core.db.model.User;
import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private boolean enabled;
    private Collection<?> authorities;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.authorities = user.getAuthorities();
    }

    public static UserDto fromUser(User user) {
        return new UserDto(user);
    }
}
