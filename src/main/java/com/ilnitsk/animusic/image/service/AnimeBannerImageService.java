package com.ilnitsk.animusic.image.service;

import com.ilnitsk.animusic.image.dao.AnimeBannerImage;
import com.ilnitsk.animusic.image.dao.Image;
import com.ilnitsk.animusic.image.repository.AnimeBannerImageRepository;
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
    public AnimeBannerImage createAnimeBannerImage(
            String animeFolder, String imageName, MultipartFile imageFile, AnimeBannerImage banner) {
        Image image = imageService.createImage(animeFolder,imageName,imageFile);
        banner.setImage(image);
        return bannerImageRepository.save(banner);
    }
}
