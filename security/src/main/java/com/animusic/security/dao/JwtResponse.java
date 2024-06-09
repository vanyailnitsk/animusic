package com.animusic.security.dao;

import com.animusic.user.dao.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseCookie;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private ResponseCookie refreshTokenCookie;
    private User user;
}
