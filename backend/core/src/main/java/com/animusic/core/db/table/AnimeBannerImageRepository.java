package com.animusic.core.db.table;

import com.animusic.core.db.model.AnimeBannerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeBannerImageRepository extends JpaRepository<AnimeBannerImage,Integer> {
}
