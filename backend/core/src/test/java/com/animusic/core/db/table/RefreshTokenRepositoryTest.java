package com.animusic.core.db.table;

import java.time.Instant;

import com.animusic.core.db.DatabaseTest;
import com.animusic.core.db.model.RefreshToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Sql
class RefreshTokenRepositoryTest extends DatabaseTest {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    void findByTokenExists() {
        var user = userRepository.findById(1).get();
        var refreshToken = RefreshToken.builder()
                .token("some-token")
                .user(user)
                .expireDate(Instant.now())
                .build();
        refreshTokenRepository.save(refreshToken);
        assertThat(refreshTokenRepository.findByToken("some-token").get()).isEqualTo(refreshToken);
    }

    @Test
    void findByTokenNotExists() {
        var user = userRepository.findById(1).get();
        var refreshToken = RefreshToken.builder()
                .token("some-token")
                .user(user)
                .expireDate(Instant.now())
                .build();
        refreshTokenRepository.save(refreshToken);
        assertThat(refreshTokenRepository.findByToken("not-existing-token")).isEmpty();
    }
}