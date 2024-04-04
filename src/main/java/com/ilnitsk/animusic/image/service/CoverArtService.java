package com.ilnitsk.animusic.image.service;

import com.ilnitsk.animusic.image.dao.CoverArt;
import com.ilnitsk.animusic.image.dao.Image;
import com.ilnitsk.animusic.image.repository.CoverArtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CoverArtService {
    private final CoverArtRepository coverArtRepository;
    private final ImageService imageService;

    public CoverArt createCoverArt(String animeFolder, String imageName, MultipartFile imageFile,CoverArt coverArt) {
        Image image = imageService.createImage(animeFolder,imageName,imageFile);
        coverArt.setImage(image);
        return coverArtRepository.save(coverArt);
    }
}
