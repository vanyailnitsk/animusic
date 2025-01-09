package animusic.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import animusic.api.dto.UserDto;
import animusic.api.mappers.UserMapper;
import animusic.core.db.model.User;
import animusic.service.security.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "REST API для управления данными пользователя", description = "Предоставляет методы для управления " +
        "данными пользователя")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    @Operation(summary = "Метод для получения данных пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение данных пользователя"),
            @ApiResponse(responseCode = "401", description = "Не авторизован"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public UserDto getUser(@AuthenticationPrincipal User currentUser) {
        return UserMapper.fromUser(currentUser);
    }

}
