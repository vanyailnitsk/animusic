package com.ilnitsk.animusic.file;

import com.ilnitsk.animusic.exception.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "storage.directory=/Users/admin/Music/animusic"
})
class FileServiceTest {
    private FileService fileService;

    @BeforeEach
    void setupTest() {
        fileService = new FileService();
        ReflectionTestUtils.setField(fileService,"storagePath","/Users/admin/Music/animusic");
    }


    private String animeFolder = "test";
    private String audioFile = "file.mp3";
    private String imageFile = "img.jpg";



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
        System.out.println(file.getOriginalFilename());
        fileService.saveAudio(file,animeFolder,"opening");
        byte[] savedFile = fileService.getFileBytes(animeFolder,"audio","opening.mp3");
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

}