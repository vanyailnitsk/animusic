package com.ilnitsk.animusic.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;

    public TokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
