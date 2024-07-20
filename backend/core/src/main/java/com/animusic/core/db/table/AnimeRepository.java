package com.animusic.core.db.table;

import java.util.List;
import java.util.Optional;

import com.animusic.core.db.model.Anime;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public interface AnimeRepository extends RepositoryBase<Anime, Integer> {
    Optional<Anime> findAnimeByTitle(String title);

    boolean existsAnimeByTitle(String title);

    List<Anime> findAllOrderByTitle();

    @RequiredArgsConstructor
    class Impl implements AnimeRepository {
        @PersistenceContext
        private final EntityManager entityManager;

        @Override
        public Optional<Anime> findById(Integer id) {
            return Optional.ofNullable(entityManager.find(Anime.class, id));
        }

        @Override
        public boolean existsById(Integer id) {
            return entityManager.find(Anime.class, id) != null;
        }

        @Override
        public long count() {
            return entityManager.createQuery("SELECT COUNT(a) FROM Anime a", Long.class).getSingleResult();
        }

        @Override
        public void deleteById(Integer id) {
            entityManager.createQuery("DELETE FROM Anime a where a.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        }

        @Override
        public void delete(Anime entity) {
            entityManager.remove(entity);
        }

        @Override
        public List<Anime> findAll() {
            return entityManager.createQuery("SELECT a FROM Anime a", Anime.class).getResultList();
        }

        @Override
        public Anime save(Anime anime) {
            if (anime.getId() == null) {
                entityManager.persist(anime);
                return anime;
            } else {
                return entityManager.merge(anime);
            }
        }

        @Override
        public Optional<Anime> findAnimeByTitle(String title) {
            return entityManager.createQuery("SELECT a FROM Anime a WHERE a.title = :title", Anime.class)
                    .setParameter("title", title)
                    .getResultStream()
                    .findAny();
        }

        @Override
        public boolean existsAnimeByTitle(String title) {
            return entityManager
                    .createQuery("SELECT COUNT(*) > 0 FROM Anime a WHERE a.title = :title", Boolean.class)
                    .setParameter("title", title)
                    .getSingleResult();
        }

        @Override
        public List<Anime> findAllOrderByTitle() {
            return entityManager.createQuery("SELECT a FROM Anime a ORDER BY a.title", Anime.class)
                    .getResultList();
        }

        @Override
        public void deleteAll() {
            entityManager.createQuery("DELETE from Anime a").executeUpdate();
        }
    }
}
