package com.ilnitsk.animusic.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@Schema(name = "Пара JWT-токенов",description = "Access и Refresh токены для авторизации и аутенфикации")
public class TokenDto {
    @Schema(name = "accessToken",description = "JWT-токен доступа к ресурсам",example = "eyJhbGciOiJIUzI1NiIsInR5cCI6")
    private String accessToken;
    @Schema(name = "refreshToken",description = "Одноразовый токен для получения нового access-токена",example = "JzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikpva")
    private String refreshToken;

    public TokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
