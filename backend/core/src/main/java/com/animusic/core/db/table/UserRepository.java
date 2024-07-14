package com.animusic.core.db.table;

import java.util.Optional;

import com.animusic.core.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
