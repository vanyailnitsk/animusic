package com.animusic.security.dto;

public record AuthRequest(
        String email,
        String password
) {
}
