package com.ilnitsk.animusic.security.service;

import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.exception.InvalidTokenException;
import com.ilnitsk.animusic.playlist.dao.Playlist;
import com.ilnitsk.animusic.playlist.repository.PlaylistRepository;
import com.ilnitsk.animusic.security.dao.RefreshToken;
import com.ilnitsk.animusic.security.dto.AuthRequest;
import com.ilnitsk.animusic.security.dto.JwtResponse;
import com.ilnitsk.animusic.security.dto.RegisterRequest;
import com.ilnitsk.animusic.security.repository.RefreshTokenRepository;
import com.ilnitsk.animusic.user.dao.User;
import com.ilnitsk.animusic.user.dto.UserDto;
import com.ilnitsk.animusic.user.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationProvider authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final PlaylistRepository playlistRepository;

    @Transactional
    public JwtResponse register(RegisterRequest registerRequest) {
        User user = registerRequest.toUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Playlist playlist = Playlist.builder().name("Favourite tracks").user(user).build();
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BadRequestException("Username уже занят!");
        }
        user.setFavouriteTracks(playlist);
        userRepository.save(user);
        String jwt = jwtService.createToken(user);
        ResponseCookie responseCookie = refreshTokenService.generateRefreshCookie(user);
        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshToken(responseCookie)
                .user(new UserDto(user))
                .build();
    }

    @Transactional
    public JwtResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));
        String jwt = jwtService.createToken(user);
        ResponseCookie responseCookie = refreshTokenService.generateRefreshCookie(user);
        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshToken(responseCookie)
                .user(new UserDto(user))
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
                .refreshToken(responseCookie)
                .user(new UserDto(user))
                .build();
    }
}
