package com.ilnitsk.animusic.security.controller;

import com.ilnitsk.animusic.security.dto.AuthRequest;
import com.ilnitsk.animusic.security.service.AuthService;
import com.ilnitsk.animusic.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Object> addNewUser(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(
            @RequestBody AuthRequest request
    ) {
        log.info("login={}",request.getUsername());
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
