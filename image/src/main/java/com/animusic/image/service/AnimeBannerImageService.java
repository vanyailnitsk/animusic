package com.animusic.image.service;

import com.animusic.image.dao.AnimeBannerImage;
import com.animusic.image.dao.Image;
import com.animusic.image.repository.AnimeBannerImageRepository;
import com.ilnitsk.animusic.anime.dao.Anime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AnimeBannerImageService {
    private final AnimeBannerImageRepository bannerImageRepository;
    private final ImageService imageService;

    @Transactional
    public AnimeBannerImage createAnimeBannerImage(Anime anime, MultipartFile imageFile, AnimeBannerImage banner) {
        Image image = imageService.createAnimeImage(anime.getFolderName(),"banner",imageFile);
        banner.setImage(image);
        return bannerImageRepository.save(banner);
    }
}
