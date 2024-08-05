package com.animusic.api.dto;

import java.util.Collection;

import lombok.Value;

@Value
public class UserDto {
    Integer id;
    String username;
    String email;
    boolean enabled;
    Collection<?> authorities;
}
