package com.animusic.security.dto;

import com.animusic.core.db.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;

    public User toUser() {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
