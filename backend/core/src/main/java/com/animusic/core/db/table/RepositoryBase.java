package com.animusic.core.db.table;

import java.util.List;
import java.util.Optional;

public interface RepositoryBase<E, I> {

    default List<E> saveAll(Iterable<E> entities) {
        entities.forEach(this::save);
        return (List<E>) entities;
    }

    List<E> findAll();

    E save(E entity);

    Optional<E> findById(I id);

    boolean existsById(I id);

    long count();

    void deleteById(I id);

    void delete(E entity);

    default void deleteAllById(Iterable<I> ids) {
        ids.forEach(this::deleteById);
    }

    default void deleteAll(Iterable<E> entities) {
        entities.forEach(this::delete);
    }

    void deleteAll();
}
