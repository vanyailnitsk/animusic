package com.animusic.core.db.table;

import com.animusic.core.db.model.Soundtrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoundtrackRepository extends JpaRepository<Soundtrack, Integer> {
}
