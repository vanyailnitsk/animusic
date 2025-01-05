package animusic.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import animusic.api.BadRequestException;
import animusic.api.mappers.AuthMapper;
import animusic.security.AuthService;
import animusic.security.JwtResponse;

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
                .body(AuthMapper.fromJwtResponse(response));
    }

    @PostMapping("/login")
    @Operation(summary = "Метод для аутентификации пользователя в системе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход в систему."),
            @ApiResponse(responseCode = "403", description = "Ошибка во время аутентификации!"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public ResponseEntity<?> authenticate(
            @RequestBody AuthRequest request
    ) {
        JwtResponse response;
        if (!request.isOAuth && StringUtils.isNotEmpty(request.email) && StringUtils.isNotEmpty(request.password)) {
            response = authService.loginByPassword(request.email, request.password);
        } else if (request.isOAuth) {
            response = authService.externalLogin(request.provider, request.code, request.state, request.redirectUri);
        } else {
            throw new BadRequestException("Invalid auth params");
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, response.getRefreshTokenCookie().toString())
                .body(AuthMapper.fromJwtResponse(response));
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
                .body(AuthMapper.fromJwtResponse(response));
    }

    public record AuthRequest(
            String email,
            String password,
            boolean isOAuth,
            String provider,
            String code,
            String state,
            String redirectUri
    ) {
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;
    }
}
