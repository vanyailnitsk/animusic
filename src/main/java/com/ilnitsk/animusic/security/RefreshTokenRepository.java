package com.ilnitsk.animusic.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByValue(String value);
}
