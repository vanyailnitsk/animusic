package com.ilnitsk.animusic.file;

import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.exception.FileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileService {
    @Value("${images.directory}")
    private String imagesPath;
    @Value("${audiotracks.directory}")
    private String audioPath;
    @Value("${storage.directory}")
    private String storagePath;

    public byte[] getFileBytes(String path, String fileName) {
        Path filePath = Paths.get(path).resolve(fileName);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(filePath.toString());
        }
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file: %s".formatted(filePath.toString()));
        }
    }

    public byte[] getImageContent(String fileName) {
        return getFileBytes(imagesPath, fileName);
    }

    public void downloadAudio(MultipartFile audioFile, String animeFolder, String fileName) throws IOException{
        Path newFilePath = Paths.get(animeFolder, fileName + getFileExtension(fileName));
        Files.deleteIfExists(newFilePath);
        try {
            handleFolderExisting(animeFolder);
            FileCopyUtils.copy(audioFile.getInputStream(), new FileOutputStream(newFilePath.toFile()));
        } catch (IOException e) {
            log.error("Error with file {}", newFilePath);
            throw new BadRequestException("Error with file " + newFilePath);
        }
    }

    public String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
    private void handleFolderExisting(String folderName) throws IOException {
        Path path = Paths.get(storagePath,folderName);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            log.info("Папка для аниме {} успешно создана.", path.getFileName().toString());
        }
    }


}
