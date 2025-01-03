package animusic.api.mappers;

import org.springframework.stereotype.Component;

import animusic.api.dto.JwtResponseDto;
import animusic.security.JwtResponse;

@Component
public class AuthMapper {

    public static JwtResponseDto fromJwtResponse(JwtResponse jwtResponse) {
        var user = UserMapper.fromUser(jwtResponse.getUser());
        return new JwtResponseDto(
                jwtResponse.getAccessToken(),
                jwtResponse.getRefreshTokenCookie(),
                user
        );
    }
}
