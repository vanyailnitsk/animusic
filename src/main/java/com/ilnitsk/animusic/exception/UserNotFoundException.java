package com.ilnitsk.animusic.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String name) {
        super("User "+name+" not found");
    }

    public UserNotFoundException(Integer userId) {
        super("User with id %d".formatted(userId));
    }
}
