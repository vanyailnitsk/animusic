package com.ilnitsk.animusic.security.service;

import com.ilnitsk.animusic.exception.TokenRefreshException;
import com.ilnitsk.animusic.security.RefreshToken;
import com.ilnitsk.animusic.security.RefreshTokenRepository;
import com.ilnitsk.animusic.user.dao.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final Integer refreshTTLhours;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               @Value("${token.refreshExpirationHours}") Integer refreshTTLhours) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTTLhours = refreshTTLhours;
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .expireDate(Instant.now().plusSeconds(refreshTTLhours*60*60))
                .token(UUID.randomUUID().toString())
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpireDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh token expired");
        }
        return refreshToken;
    }
}
