package com.animusic.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super("User email={%s} not found".formatted(email));
    }

    public UserNotFoundException(Integer id) {
        super("User id={%d} not found".formatted(id));
    }
}
