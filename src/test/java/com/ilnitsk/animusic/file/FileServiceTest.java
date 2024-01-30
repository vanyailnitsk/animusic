package com.ilnitsk.animusic.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FileServiceTest {
    @Autowired
    private FileService fileService;
    private String animeFolder = "test";
    private String audioFile = "file.mp3";
    private String imageFile = "img.jpg";



    @Test
    void getFileBytes() {
        fileService.getFileBytes(animeFolder,"audio",audioFile);
    }

    @Test
    void getImageContent() {
        fileService.getImageContent(animeFolder,imageFile);
    }

    @Test
    void saveFile() {
    }

    @Test
    void downloadAudio() {
    }

    @Test
    void getFileExtension() {
    }
}