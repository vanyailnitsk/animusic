package com.ilnitsk.animusic.security.service;

import com.ilnitsk.animusic.security.dto.TokenDto;
import com.ilnitsk.animusic.user.dao.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class JwtService {

    private final String secret_key;
    private long TTL_MINUTES = 60;
    private long REFRESH_TTL_DAYS = 30;

    private final JwtParser jwtParser;

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public JwtService(@Value("${token.secret}") String secret){
        this.secret_key = secret;
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    public TokenDto createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        ZonedDateTime tokenCreateTime = ZonedDateTime.now();
        Date tokenValidity = Date.from(tokenCreateTime.plusMinutes(TTL_MINUTES).toInstant());
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
        Date refreshTokenValidity = Date.from(tokenCreateTime.plusDays(REFRESH_TTL_DAYS).toInstant());
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(refreshTokenValidity)
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String parseUsernameFromJwt(String token) {
        return parseJwtClaims(token).getSubject();
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            log.warn("Claims is invalid: {}",claims.getSubject());
            return false;
        }
    }

    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }
}
