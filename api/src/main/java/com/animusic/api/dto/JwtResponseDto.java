package com.animusic.api.dto;

import com.animusic.security.dao.JwtResponse;
import com.animusic.user.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseCookie;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(name = "JWT-ответ",description = "Ответ на авторизацию")
public class JwtResponseDto {
    @Schema(name = "accessToken",description = "JWT-токен доступа к ресурсам",example = "eyJhbGciOiJIUzI1NiIsInR5cCI6")
    private String accessToken;
    @JsonIgnore
    @Schema(name = "refreshToken",description = "Одноразовый токен для получения нового access-токена",example = "JzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikpva")
    private ResponseCookie refreshToken;
    @Schema(name = "user",description = "Данные об авторизованном пользователе")
    private UserDto user;

    public static JwtResponseDto fromJwtResponse(JwtResponse jwtResponse) {
        return new JwtResponseDto(jwtResponse.getAccessToken(),jwtResponse.getRefreshTokenCookie(), UserDto.fromUser(jwtResponse.getUser()));
    }
}
