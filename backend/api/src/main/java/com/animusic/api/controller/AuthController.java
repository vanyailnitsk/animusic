package com.animusic.api.controller;

import com.animusic.api.dto.JwtResponseDto;
import com.animusic.user.JwtResponse;
import com.animusic.user.dto.AuthRequest;
import com.animusic.user.dto.RegisterRequest;
import com.animusic.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
@Tag(name = "REST API для авторизации", description = "Предоставляет методы для аутентификации и авторизации")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Метод для регистрации пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная регистрация."),
            @ApiResponse(responseCode = "400", description = "Email уже занят"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        JwtResponse response = authService.register(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, response.getRefreshTokenCookie().toString())
                .body(JwtResponseDto.fromJwtResponse(response));
    }

    @PostMapping("/login")
    @Operation(summary = "Метод для аутентификации пользователя в системе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход в систему."),
            @ApiResponse(responseCode = "403", description = "Ошибка во время аутентификации!"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        JwtResponse response = authService.authenticate(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, response.getRefreshTokenCookie().toString())
                .body(JwtResponseDto.fromJwtResponse(response));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Метод для получения новой пары токенов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление токенов."),
            @ApiResponse(responseCode = "403", description = "Ошибка во время аутентификации!"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        JwtResponse response = authService.updateToken(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, response.getRefreshTokenCookie().toString())
                .body(JwtResponseDto.fromJwtResponse(response));
    }
}
