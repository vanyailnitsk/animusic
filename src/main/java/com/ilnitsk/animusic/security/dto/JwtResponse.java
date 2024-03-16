package com.ilnitsk.animusic.security.dto;

import com.ilnitsk.animusic.user.dao.User;
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
public class JwtResponse {
    @Schema(name = "accessToken",description = "JWT-токен доступа к ресурсам",example = "eyJhbGciOiJIUzI1NiIsInR5cCI6")
    private String accessToken;
    @Schema(name = "refreshToken",description = "Одноразовый токен для получения нового access-токена",example = "JzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikpva")
    private ResponseCookie refreshToken;
    @Schema(name = "user",description = "Данные об авторизованном пользователе")
    private User user;
}
