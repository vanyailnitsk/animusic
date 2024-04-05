package com.ilnitsk.animusic.image.repository;

import com.ilnitsk.animusic.image.dao.AnimeBannerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeBannerImageRepository extends JpaRepository<AnimeBannerImage,Integer> {
}
