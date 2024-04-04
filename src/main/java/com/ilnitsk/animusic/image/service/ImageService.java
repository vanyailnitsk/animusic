package com.ilnitsk.animusic.image.service;

import com.ilnitsk.animusic.image.dao.Image;
import com.ilnitsk.animusic.image.repository.ImageRepository;
import com.ilnitsk.animusic.s3.S3Service;
import com.ilnitsk.animusic.soundtrack.dao.Soundtrack;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final S3Service s3Service;
    private static final String IMAGES_PATH = "%s/images/%s";

    public void createSoundtrackImage(Soundtrack soundtrack, MultipartFile image) {
        Image imageEntity = new Image();
        String fileName = IMAGES_PATH.formatted(soundtrack.getAnime().getFolderName(),soundtrack.getAnimeTitle());
        String blobKey = s3Service.createBlob(fileName,image);
        imageEntity.setSource(blobKey);
        soundtrack.setImage(imageEntity);
    }

    public void deleteImage(Image image) {
        s3Service.deleteObject(image.getSource());
        imageRepository.delete(image);
    }

}
