package com.ilnitsk.animusic.soundtrack.repository;

import com.ilnitsk.animusic.soundtrack.dao.Soundtrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoundtrackRepository extends JpaRepository<Soundtrack,Integer> {
}
