package com.ilnitsk.animusic.file;

import com.ilnitsk.animusic.exception.FileNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
class FileServiceTest {
    private static FileService fileService;
    private static String animeFolder = "test";
    private static String audioFile = "file.mp3";
    private static String imageFile = "img.jpg";
    private static String testFolder;
    private static String dirForTests = Paths.get(System.getProperty("user.dir"),"files-test").toString();

    @BeforeAll
    static void setupTest() throws IOException {
        fileService = new FileService();
        ReflectionTestUtils.setField(fileService,"storagePath",dirForTests);
        testFolder = Paths.get(dirForTests,animeFolder).toString();
        Files.createDirectories(Paths.get(testFolder));
        Files.createDirectories(Paths.get(testFolder,"audio"));
        Files.createDirectories(Paths.get(testFolder,"images"));
        Files.createFile(Paths.get(dirForTests ,animeFolder,"audio",audioFile));
        Files.createFile(Paths.get(dirForTests ,animeFolder,"images",imageFile));
    }

    @AfterAll
    static void afterAll() throws IOException {
        Path directoryPath = Paths.get(testFolder);
        if (Files.exists(directoryPath)) {
            Files.walk(directoryPath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }



    @Test
    void saveFile() {
        fileService.saveFile(
                new MockMultipartFile("mock.png",new byte[]{1,2,3}),
                "images",
                animeFolder,
                "banner.png");
        byte[] savedFile = fileService.getFileBytes(animeFolder,"images","banner.png");
        assertThat(savedFile).isNotEmpty();
    }

    @Test
    void saveAudio() {
        MultipartFile file = new MockMultipartFile("test.mp3","test.mp3","",new byte[]{1,2,3});
        fileService.saveAudio(file,animeFolder,"opening");
        byte[] savedFile = fileService.getFileBytes(animeFolder,"audio","opening.mp3");
        assertThat(savedFile).isNotEmpty();
    }

    @Test
    void saveImage() {
        MultipartFile file = new MockMultipartFile("image.png","image.png","",new byte[]{1,2,3});
        fileService.saveImage(file,animeFolder,"banner");
        byte[] savedFile = fileService.getFileBytes(animeFolder,"images","banner.png");
        assertThat(savedFile).isNotEmpty();
    }

    @Test
    void getFileExtension() {
        String test1 = fileService.getFileExtension(audioFile);
        String test2 = fileService.getFileExtension(imageFile);
        String test3 = fileService.getFileExtension("1.3.e.f.ex");
        String test4 = fileService.getFileExtension("file");
        assertThat(test1).isEqualTo(".mp3");
        assertThat(test2).isEqualTo(".jpg");
        assertThat(test3).isEqualTo(".ex");
        assertThat(test4).isNull();
    }

    @Test
    void getFileBytesCorrect() {
        byte[] result = fileService.getFileBytes(animeFolder,"audio",audioFile);
        assertThat(result).isNotNull();
    }

    @Test
    void getFileBytesWrongFile() {
        assertThatThrownBy(() -> fileService.getFileBytes(animeFolder,"audio","notexistingfile"))
                .isInstanceOf(FileNotFoundException.class);
    }

    @Test
    void getImageContent() {
        byte[] image = fileService.getImageContent(animeFolder,imageFile);
        assertThat(image).isNotNull();
    }

}