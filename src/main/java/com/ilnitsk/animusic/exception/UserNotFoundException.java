package com.ilnitsk.animusic.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String name) {
        super("User "+name+" not found");
    }
}
