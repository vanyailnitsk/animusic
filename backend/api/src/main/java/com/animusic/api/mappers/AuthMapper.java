package com.animusic.api.mappers;

import com.animusic.api.dto.JwtResponseDto;
import com.animusic.user.JwtResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public JwtResponseDto fromJwtResponse(JwtResponse jwtResponse) {
        var user = UserMapper.fromUser(jwtResponse.getUser());
        return new JwtResponseDto(
                jwtResponse.getAccessToken(),
                jwtResponse.getRefreshTokenCookie(),
                user
        );
    }
}
