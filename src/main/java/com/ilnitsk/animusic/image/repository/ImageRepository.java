package com.ilnitsk.animusic.image.repository;

import com.ilnitsk.animusic.image.dao.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {
}
