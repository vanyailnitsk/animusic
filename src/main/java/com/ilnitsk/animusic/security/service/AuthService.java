package com.ilnitsk.animusic.security.service;

import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.exception.InvalidTokenException;
import com.ilnitsk.animusic.exception.UserNotFoundException;
import com.ilnitsk.animusic.security.Token;
import com.ilnitsk.animusic.security.TokenRepository;
import com.ilnitsk.animusic.security.dto.AuthRequest;
import com.ilnitsk.animusic.security.dto.TokenDto;
import com.ilnitsk.animusic.user.User;
import com.ilnitsk.animusic.user.UserService;
import com.ilnitsk.animusic.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationProvider authenticationManager;
    private final TokenRepository tokenRepository;
    @Transactional
    public TokenDto register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new BadRequestException("Username уже занят!");
        }
        userRepository.save(user);
        TokenDto tokens = jwtService.createToken(user);
        tokenRepository.save(new Token(tokens.getRefreshToken()));
        return tokens;
    }

    @Transactional
    public TokenDto authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));
        TokenDto tokens = jwtService.createToken(user);
        tokenRepository.save(new Token(tokens.getRefreshToken()));
        return tokens;
    }


    @Transactional
    public TokenDto updateToken(String refreshToken) {
        String username = jwtService.parseUsernameFromJwt(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        Token token = tokenRepository.findByValue(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("Refresh-токен не найден!"));
        tokenRepository.delete(token);
        TokenDto tokenDto = jwtService.createToken(user);
        tokenRepository.save(new Token(tokenDto.getRefreshToken()));
        return tokenDto;
    }
}
