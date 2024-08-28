package com.animusic.core.conf;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.animusic.common.ProfilesConfiguration;
import com.animusic.core.db.table.AlbumRepository;
import com.animusic.core.db.table.AnimeBannerImageRepository;
import com.animusic.core.db.table.AnimeRepository;
import com.animusic.core.db.table.CoverArtRepository;
import com.animusic.core.db.table.ImageRepository;
import com.animusic.core.db.table.PlaylistRepository;
import com.animusic.core.db.table.PlaylistSoundtrackRepository;
import com.animusic.core.db.table.RefreshTokenRepository;
import com.animusic.core.db.table.SoundtrackRepository;
import com.animusic.core.db.table.TrackListeningEventRepository;
import com.animusic.core.db.table.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Profiles;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import({ProfilesConfiguration.class})
@EnableJpaRepositories(basePackages = "com.animusic.core.db.table")
@EntityScan(basePackages = {"com.animusic.core.db.model"})
@EnableTransactionManagement
@EnableAutoConfiguration
@Slf4j
public class DatabaseConfig {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    public void setup() throws SQLException {
        log.info("Using database: {}", dataSource.getConnection().getMetaData().getURL());
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase/changelog.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

    @EventListener
    public void loadData(ApplicationReadyEvent event) {
        if (!event.getApplicationContext().getEnvironment().acceptsProfiles(Profiles.of("dev"))) {
            return;
        }
        var script = "initdb-dev.sql";
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(
                true,
                false,
                "UTF-8",
                new ClassPathResource(script)
        );
        resourceDatabasePopulator.execute(dataSource);
        log.info("Script {} was executed", script);
    }

    @Bean
    public AlbumRepository albumRepository(EntityManager entityManager) {
        return new AlbumRepository.Impl(entityManager);
    }

    @Bean
    public AnimeRepository animeRepository(EntityManager entityManager) {
        return new AnimeRepository.Impl(entityManager);
    }

    @Bean
    public AnimeBannerImageRepository animeBannerImageRepository(EntityManager entityManager) {
        return new AnimeBannerImageRepository.Impl(entityManager);
    }

    @Bean
    public CoverArtRepository coverArtRepository(EntityManager entityManager) {
        return new CoverArtRepository.Impl(entityManager);
    }

    @Bean
    public ImageRepository imageRepository(EntityManager entityManager) {
        return new ImageRepository.Impl(entityManager);
    }

    @Bean
    public PlaylistRepository playlistRepository(EntityManager entityManager) {
        return new PlaylistRepository.Impl(entityManager);
    }

    @Bean
    public PlaylistSoundtrackRepository playlistSoundtrackRepository(EntityManager entityManager) {
        return new PlaylistSoundtrackRepository.Impl(entityManager);
    }

    @Bean
    public SoundtrackRepository soundtrackRepository(EntityManager entityManager) {
        return new SoundtrackRepository.Impl(entityManager);
    }

    @Bean
    public UserRepository userRepository(EntityManager entityManager) {
        return new UserRepository.Impl(entityManager);
    }

    @Bean
    public RefreshTokenRepository refreshTokenRepository(EntityManager entityManager) {
        return new RefreshTokenRepository.Impl(entityManager);
    }

    @Bean
    public TrackListeningEventRepository trackListeningEventRepository(EntityManager entityManager) {
        return new TrackListeningEventRepository.Impl(entityManager);
    }

}
