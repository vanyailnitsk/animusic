package com.ilnitsk.users.security;

import com.ilnitsk.users.UserRepository;
import com.ilnitsk.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> credential = repository.findByUsername(username);
        return credential.orElseThrow(
                () -> new UsernameNotFoundException(username)
        );
    }
}