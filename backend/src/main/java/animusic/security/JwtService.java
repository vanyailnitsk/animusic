package animusic.security;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import animusic.conf.properties.JwtProperties;
import animusic.core.db.model.User;
import animusic.util.CookieUtils;

@Service
@Slf4j
public class JwtService {

    private final String SECRET_KEY;

    private final long TTL_HOURS;

    private final long REFRESH_TTL_HOURS;

    private final String TOKEN_HEADER = "Authorization";

    private final String TOKEN_PREFIX = "Bearer ";

    private CookieUtils cookieUtils;

    @Autowired
    public JwtService(CookieUtils cookieUtils, JwtProperties jwtProperties) {
        this.cookieUtils = cookieUtils;
        this.SECRET_KEY = jwtProperties.getSecret();
        this.TTL_HOURS = jwtProperties.getExpirationHours();
        this.REFRESH_TTL_HOURS = jwtProperties.getRefreshExpirationHours();
    }

    public String createToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        ZonedDateTime tokenCreateTime = ZonedDateTime.now();
        Date tokenValidity = Date.from(tokenCreateTime.plusHours(TTL_HOURS).toInstant());
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .expiration(tokenValidity)
                .issuedAt(new Date())
                .and()
                .signWith(getSecretKey())
                .compact();
    }

    public ResponseCookie generateJwtCookie(User user) {
        String jwt = createToken(user);
        return cookieUtils.generateCookie("access-token", jwt, "/api");
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return extractAllClaims(token);
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
            log.warn("Claims is invalid: {}", claims.getSubject());
            return false;
        }
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
