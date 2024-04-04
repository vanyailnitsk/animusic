package com.ilnitsk.animusic.image.repository;

import com.ilnitsk.animusic.image.dao.CoverArt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverArtRepository extends JpaRepository<CoverArt,Integer> {
}
