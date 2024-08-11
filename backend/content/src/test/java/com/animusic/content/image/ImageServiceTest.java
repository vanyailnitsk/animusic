package com.animusic.content.image;

import com.animusic.content.IntegrationTestBase;
import com.animusic.core.db.model.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class ImageServiceTest extends IntegrationTestBase {

    @Autowired
    ImageService imageService;

    @Test
    void createImageInAnimeDirectory() {
        var imageFile = new MockMultipartFile("image", "logo.png", "img/png", "image".getBytes());
        var image = imageService.createImageInAnimeDirectory("Anime1", "banner", imageFile);

        var expectedImage = "Anime1/images/banner.png";
        assertThat(s3Service.getObject(expectedImage)).isEqualTo("image".getBytes());
        assertThat(image.getSource()).isEqualTo(expectedImage);
    }

    @Test
    void createImageForUser() {
        var imageFile = new MockMultipartFile("file", "logo.jpeg", "img/jpg", "avatar".getBytes());
        var image = imageService.createImageForUser(1, "avatar", imageFile);

        var expectedImage = "users/1/avatar.jpeg";
        assertThat(s3Service.getObject(expectedImage)).isEqualTo("avatar".getBytes());
        assertThat(image.getSource()).isEqualTo(expectedImage);
    }

    @Test
    void deleteImage() {
        var image = imageRepository.save(Image.builder().source("123").build());
        var id = image.getId();
        assertThat(imageRepository.findById(id)).isNotEmpty();

        imageService.deleteImage(image);
        assertThat(imageRepository.findById(id)).isEmpty();
    }
}