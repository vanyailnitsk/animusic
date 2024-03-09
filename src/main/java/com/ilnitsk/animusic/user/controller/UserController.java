package com.ilnitsk.animusic.user.controller;

import com.ilnitsk.animusic.user.dao.User;
import com.ilnitsk.animusic.user.dto.UserDto;
import com.ilnitsk.animusic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public UserDto getUser() {
        User user = userService.getUserInSession();
        return new UserDto(user);
    }

}
