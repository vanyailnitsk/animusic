package com.ilnitsk.animusic.security.service;

import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.exception.InvalidTokenException;
import com.ilnitsk.animusic.exception.UserNotFoundException;
import com.ilnitsk.animusic.security.RefreshToken;
import com.ilnitsk.animusic.security.RefreshTokenRepository;
import com.ilnitsk.animusic.security.dto.AuthRequest;
import com.ilnitsk.animusic.security.dto.RegisterRequest;
import com.ilnitsk.animusic.security.dto.TokenDto;
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
    @Transactional
    public TokenDto register(RegisterRequest registerRequest) {
        User user = registerRequest.toUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BadRequestException("Username уже занят!");
        }
        userRepository.save(user);
        TokenDto tokens = jwtService.createToken(user);
        refreshTokenRepository.save(new RefreshToken(tokens.getRefreshToken()));
        return tokens;
    }

    @Transactional
    public TokenDto authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));
        TokenDto tokens = jwtService.createToken(user);
        refreshTokenRepository.save(new RefreshToken(tokens.getRefreshToken()));
        return tokens;
    }


    @Transactional
    public TokenDto updateToken(String refreshToken) {
        String email = jwtService.parseUsernameFromJwt(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        RefreshToken token = refreshTokenRepository.findByValue(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("Refresh-токен не найден!"));
        refreshTokenRepository.delete(token);
        TokenDto tokenDto = jwtService.createToken(user);
        refreshTokenRepository.save(new RefreshToken(tokenDto.getRefreshToken()));
        return tokenDto;
    }
}
