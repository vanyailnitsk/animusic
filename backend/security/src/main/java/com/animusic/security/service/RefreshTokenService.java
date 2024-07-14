package com.animusic.security.service;

import com.animusic.core.CookieUtils;
import com.animusic.core.db.model.User;
import com.animusic.security.TokenRefreshException;
import com.animusic.security.dao.RefreshToken;
import com.animusic.security.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final Integer refreshTTLhours;
    private final CookieUtils cookieUtils;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               @Value("${token.refreshExpirationHours}") Integer refreshTTLhours,
                               CookieUtils cookieUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTTLhours = refreshTTLhours;
        this.cookieUtils = cookieUtils;
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

    public ResponseCookie generateRefreshCookie(User user) {
        String refreshToken = createRefreshToken(user).getToken();
        return cookieUtils.generateCookie("refresh-token",refreshToken,"/api/auth/refresh");
    }

    public String getRefreshFromCookie(HttpServletRequest request) {
        return cookieUtils.getCookieValueByName(request,"refresh-token");
    }
}
