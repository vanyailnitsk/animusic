package com.animusic.security.service;

import java.util.Optional;

import com.animusic.core.db.model.User;
import com.animusic.core.db.table.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> credential = repository.findByEmail(username);
        return credential.orElseThrow(
                () -> new UsernameNotFoundException(username)
        );
    }
}
