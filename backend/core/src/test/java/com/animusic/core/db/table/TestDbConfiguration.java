package com.animusic.core.db.table;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.animusic.core.db.model.Anime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@PropertySource("classpath:application-testing.yaml")
@ActiveProfiles("testing")
public class TestDbConfiguration {
    @Bean
    AnimeRepository animeRepository() {
        return new AnimeRepository() {
            @Override
            public Anime findAnimeByTitle(String title) {
                return null;
            }

            @Override
            public boolean existsAnimeByTitle(String title) {
                return false;
            }

            @Override
            public List<Anime> findAllByOrderByTitle() {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Anime> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends Anime> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<Anime> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Integer> integers) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Anime getOne(Integer integer) {
                return null;
            }

            @Override
            public Anime getById(Integer integer) {
                return null;
            }

            @Override
            public Anime getReferenceById(Integer integer) {
                return null;
            }

            @Override
            public <S extends Anime> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Anime> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public <S extends Anime> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public List<Anime> findAll() {
                return null;
            }

            @Override
            public List<Anime> findAllById(Iterable<Integer> integers) {
                return null;
            }

            @Override
            public <S extends Anime> S save(S entity) {
                return null;
            }

            @Override
            public Optional<Anime> findById(Integer integer) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Integer integer) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Integer integer) {

            }

            @Override
            public void delete(Anime entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Integer> integers) {

            }

            @Override
            public void deleteAll(Iterable<? extends Anime> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public List<Anime> findAll(Sort sort) {
                return null;
            }

            @Override
            public Page<Anime> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Anime> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Anime> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Anime> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Anime> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends Anime, R> R findBy(
                    Example<S> example,
                    Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction
            ) {
                return null;
            }
        };
    }
}
