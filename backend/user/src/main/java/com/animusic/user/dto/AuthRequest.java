package com.animusic.user.dto;

public record AuthRequest(
        String email,
        String password
) {
}
