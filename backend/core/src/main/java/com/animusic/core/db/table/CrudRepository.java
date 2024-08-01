package com.animusic.core.db.table;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CrudRepository<E, I> extends org.springframework.data.repository.CrudRepository<E, I> {
    List<E> findAll(Sort.Order order);

    @Override
    List<E> findAll();
}
