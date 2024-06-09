package com.animusic.user.service;

import com.animusic.user.UserAlreadyExistsException;
import com.animusic.user.UserNotFoundException;
import com.animusic.user.dao.User;
import com.animusic.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUserOrThrow(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with id %s already exists".formatted(user.getEmail()));
        }
        return userRepository.save(user);
    }

    public User findByEmailOrThrow(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }
}
