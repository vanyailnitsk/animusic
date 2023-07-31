package com.ilnitsk.animusic.repositories;

import com.ilnitsk.animusic.models.Soundtrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoundtrackRepository extends JpaRepository<Soundtrack,Integer> {
}
