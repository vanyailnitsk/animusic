package com.animusic.content.image;

import java.io.IOException;

import com.animusic.core.db.model.Image;
import com.animusic.core.db.table.ImageRepository;
import com.animusic.s3.S3Service;
import com.animusic.s3.StoragePathResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    private final S3Service s3Service;

    private final String CONTENT_TYPE = "image/jpeg";

    @Transactional
    public void deleteImage(Image image) {
        s3Service.deleteObject(image.getSource());
        imageRepository.delete(image);
    }

    @Transactional
    public Image createImageInAnimeDirectory(String animeFolder, String imageName, MultipartFile image) {
        Image imageEntity = new Image();
        var fileName = StoragePathResolver.imageInAnimeFolder(animeFolder, imageName, image.getOriginalFilename());
        try {
            String blobKey = s3Service.createBlob(fileName, image.getBytes(), CONTENT_TYPE);
            imageEntity.setSource(blobKey);
            return imageRepository.save(imageEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Image createImageForUser(Integer userId, String imageName, MultipartFile image) {
        Image imageEntity = new Image();
        var fileName = StoragePathResolver.imageInUserFolder(userId, imageName, image.getOriginalFilename());
        try {
            String blobKey = s3Service.createBlob(fileName, image.getBytes(), CONTENT_TYPE);
            imageEntity.setSource(blobKey);
            return imageRepository.save(imageEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Image getImage(Integer id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }

}
