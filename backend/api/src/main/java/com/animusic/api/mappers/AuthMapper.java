package com.animusic.api.mappers;

import com.animusic.api.dto.JwtResponseDto;
import com.animusic.user.JwtResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {

    @NonNull
    private UserMapper userMapper;

    public JwtResponseDto fromJwtResponse(JwtResponse jwtResponse) {
        var user = userMapper.fromUser(jwtResponse.getUser());
        return new JwtResponseDto(
                jwtResponse.getAccessToken(),
                jwtResponse.getRefreshTokenCookie(),
                user
        );
    }
}
