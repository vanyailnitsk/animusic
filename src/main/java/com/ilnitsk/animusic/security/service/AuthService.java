package com.ilnitsk.animusic.security.service;

import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.security.dto.AuthRequest;
import com.ilnitsk.animusic.user.User;
import com.ilnitsk.animusic.user.UserService;
import com.ilnitsk.animusic.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationProvider authenticationManager;

    public Map<String,String> register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String token = jwtService.createToken(user);
        return Collections.singletonMap("token",token);
    }

    public Map<String,String> authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));
        var jwtToken = jwtService.createToken(user);
        return Collections.singletonMap("token",jwtToken);
    }



}
