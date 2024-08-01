package com.animusic.core.db.table;

import java.util.Optional;

import com.animusic.core.db.model.RefreshToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@Component
@NoRepositoryBean
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    class Impl extends RepositoryBase<RefreshToken, Long> implements RefreshTokenRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, RefreshToken.class);
        }

        @Override
        public Optional<RefreshToken> findByToken(String token) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            var query = cb.createQuery(RefreshToken.class);
            var root = query.from(RefreshToken.class);
            query.select(root)
                    .where(cb.equal(root.get("token"), token));
            return getOptionalResult(entityManager.createQuery(query));

        }
    }
}
