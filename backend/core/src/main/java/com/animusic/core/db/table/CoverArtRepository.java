package com.animusic.core.db.table;

import com.animusic.core.db.model.CoverArt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverArtRepository extends JpaRepository<CoverArt,Integer> {
}
