package animusic.service.security;

import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import animusic.api.exception.UserNotFoundException;
import animusic.core.db.model.Playlist;
import animusic.core.db.model.Role;
import animusic.core.db.model.User;
import animusic.core.db.table.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUserData(String username, String email, String rawPassword) {
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .build();
        user.getRoles().add(Role.ROLE_USER);
        Playlist playlist = Playlist.builder().name("Favourite tracks").build();
        playlist.setUser(user);
        return user;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findOrCreateUserByOAuth(
            String provider,
            String id,
            String externalEmail,
            String name,
            String avatarUrl
    ) {
        Optional<User> user;

        if (provider.equals("google")) {
            user = userRepository.findByGoogleId(id);
        } else if (provider.equals("github")) {
            user = userRepository.findByGithubId(id);
        } else {
            throw new IllegalArgumentException("Unknown provider: " + provider);
        }

        if (user.isPresent()) {
            return user.get();
        }

        String userEmail = null;
        if (externalEmail != null && !userExistsByEmail(externalEmail)) {
            userEmail = externalEmail;
        }
        User newUser = createUserData(name, userEmail, UUID.randomUUID().toString());

        if (provider.equals("google")) {
            newUser.setGoogleId(id);
        } else if (provider.equals("github")) {
            newUser.setGithubId(id);
        }

        return userRepository.save(newUser);
    }


    public User findByEmailOrThrow(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public Optional<User> getUserInSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

}
