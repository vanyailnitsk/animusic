package animusic.service.image;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import animusic.core.db.model.Image;
import animusic.core.db.table.ImageRepository;
import animusic.service.s3.S3Service;
import animusic.util.StoragePathResolver;

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
        var imageEntity = new Image();
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
        var imageEntity = new Image();
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
