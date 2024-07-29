package com.animusic.core.db.table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@RequiredArgsConstructor
public abstract class RepositoryBase<E, ID> implements CrudRepository<E, ID> {
    @NonNull
    protected final EntityManager entityManager;

    @NonNull
    protected final Class<E> domainClass;

    @Transactional
    @Override
    public <S extends E> S save(S entity) {
        Assert.notNull(entity, "Entity must not be null");
        if (isNew(entity)) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    @Override
    public <S extends E> Iterable<S> saveAll(Iterable<S> entities) {
        Assert.notNull(entities, "Entities must not be null");

        List<S> result = new ArrayList<>();

        for (S entity : entities) {
            result.add(save(entity));
        }

        return result;
    }

    @Override
    public Optional<E> findById(ID id) {
        Assert.notNull(id, "The given id must not be null");
        return Optional.ofNullable(entityManager.find(domainClass, id));
    }

    @Override
    public boolean existsById(@NotNull ID id) {
        return findById(id).isPresent();
    }

    @Override
    public List<E> findAll() {
        return findAll(Sort.Order.asc("id"));
    }

    public List<E> findAll(Sort.Order order) {
        return getQuery(order).getResultList();
    }

    @Override
    public Iterable<E> findAllById(Iterable<ID> ids) {
        Assert.notNull(ids, "Ids must not be null");

        if (!ids.iterator().hasNext()) {
            return Collections.emptyList();
        }

        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(domainClass);
        Root<E> root = cq.from(domainClass);
        cq.where(root.get("id").in(ids));
        TypedQuery<E> query = getQuery(null);

        return query.getResultList();
    }

    @Override
    public long count() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<E> root = query.from(domainClass);
        query.select(builder.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public void deleteById(ID id) {
        findById(id).ifPresent(this::delete);
    }

    @Override
    public void delete(E entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends E> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<E> delete = cb.createCriteriaDelete(domainClass);
        delete.from(domainClass);
        entityManager.createQuery(delete).executeUpdate();
    }

    protected TypedQuery<E> getQuery(@Nullable Sort.Order order) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(domainClass);

        Root<E> root = query.from(domainClass);
        query.select(root);

        if (order != null) {
            query.orderBy(toJpaOrder(order, root, builder));
        }

        return entityManager.createQuery(query);
    }

    private static Order toJpaOrder(Sort.Order order, From<?, ?> from, CriteriaBuilder cb) {
        Expression<?> expression = from.get(order.getProperty());
        return order.isAscending() ? cb.asc(expression) : cb.desc(expression);
    }

    private boolean isNew(E entity) {
        try {
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            return field.get(entity) == null;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("No `id` field in entity!");
        }
    }
}
