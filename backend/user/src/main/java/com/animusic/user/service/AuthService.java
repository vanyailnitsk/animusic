package com.animusic.user.service;

import com.animusic.core.db.model.Playlist;
import com.animusic.core.db.model.RefreshToken;
import com.animusic.core.db.model.Role;
import com.animusic.core.db.model.User;
import com.animusic.core.db.table.RefreshTokenRepository;
import com.animusic.user.InvalidTokenException;
import com.animusic.user.JwtResponse;
import com.animusic.user.dto.AuthRequest;
import com.animusic.user.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationProvider authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    @Transactional
    public JwtResponse register(RegisterRequest registerRequest) {
        User user = registerRequest.toUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        Playlist playlist = Playlist.builder().name("Favourite tracks").build();
        playlist.setUser(user);
        userService.createUserOrThrow(user);
        String jwt = jwtService.createToken(user);
        ResponseCookie responseCookie = refreshTokenService.generateRefreshCookie(user);
        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshTokenCookie(responseCookie)
                .user(user)
                .build();
    }

    @Transactional
    public JwtResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = userService.findByEmailOrThrow(request.email());
        String jwt = jwtService.createToken(user);
        ResponseCookie responseCookie = refreshTokenService.generateRefreshCookie(user);
        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshTokenCookie(responseCookie)
                .user(user)
                .build();
    }

    @Transactional
    public JwtResponse updateToken(HttpServletRequest request) {
        String refreshToken = refreshTokenService.getRefreshFromCookie(request);
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("Refresh-токен не найден!"));
        refreshTokenService.verifyExpiration(token);
        User user = token.getUser();
        refreshTokenRepository.deleteById(token.getId());
        String jwt = jwtService.createToken(user);
        ResponseCookie responseCookie = refreshTokenService.generateRefreshCookie(user);
        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshTokenCookie(responseCookie)
                .user(user)
                .build();
    }
}
