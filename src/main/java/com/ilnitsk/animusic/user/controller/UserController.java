package com.ilnitsk.animusic.user.controller;

import com.ilnitsk.animusic.user.dao.User;
import com.ilnitsk.animusic.user.dto.UserDto;
import com.ilnitsk.animusic.user.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "REST API для управления данными пользователя", description = "Предоставляет методы для управления данными пользователя")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение данных пользователя"),
            @ApiResponse(responseCode = "401", description = "Не авторизован"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public UserDto getUser() {
        User user = userService.getUserInSession();
        return new UserDto(user);
    }

}
