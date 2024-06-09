package com.animusic.soundtrack.repository;

import com.animusic.soundtrack.dao.Soundtrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoundtrackRepository extends JpaRepository<Soundtrack,Integer> {
}
