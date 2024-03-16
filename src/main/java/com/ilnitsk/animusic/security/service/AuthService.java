package com.ilnitsk.animusic.security.service;

import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.exception.InvalidTokenException;
import com.ilnitsk.animusic.security.RefreshToken;
import com.ilnitsk.animusic.security.RefreshTokenRepository;
import com.ilnitsk.animusic.security.dto.AuthRequest;
import com.ilnitsk.animusic.security.dto.JwtResponse;
import com.ilnitsk.animusic.security.dto.RegisterRequest;
import com.ilnitsk.animusic.user.dao.User;
import com.ilnitsk.animusic.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public JwtResponse register(RegisterRequest registerRequest) {
        User user = registerRequest.toUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BadRequestException("Username уже занят!");
        }
        userRepository.save(user);
        String jwt = jwtService.createToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken.getToken())
                .user(user)
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
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken.getToken())
                .user(user)
                .build();
    }


    @Transactional
    public JwtResponse updateToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("Refresh-токен не найден!"));
        refreshTokenService.verifyExpiration(token);
        User user = token.getUser();
        refreshTokenRepository.delete(token);
        String jwt = jwtService.createToken(user);
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);
        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshToken(newRefreshToken.getToken())
                .user(user)
                .build();
    }
}
