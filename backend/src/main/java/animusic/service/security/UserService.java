package animusic.service.security;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import animusic.api.exception.UserAlreadyExistsException;
import animusic.api.exception.UserNotFoundException;
import animusic.core.db.model.User;
import animusic.core.db.table.UserRepository;

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

    public Optional<User> getUserInSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
