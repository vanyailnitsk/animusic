package com.ilnitsk.animusic.file;

import com.ilnitsk.animusic.exception.FileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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


    public byte[] getFileBytes(String animeFolder,String subDirectory,String fileName) {
        Path filePath = Paths.get(storagePath,animeFolder,subDirectory,fileName);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(filePath.toString());
        }
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file: %s".formatted(filePath.toString()));
        }
    }

    public byte[] getImageContent(String animeFolder,String fileName) {
        return getFileBytes(animeFolder, "images",fileName);
    }

    public void saveFile(MultipartFile file, String subDirectory,String animeFolder, String fileName) {
        try {
            Path folderPath = Paths.get(storagePath, animeFolder,subDirectory);
            handleFolderExisting(folderPath);
            Path filePath = Paths.get(folderPath.toString(),fileName);
            if (Files.exists(filePath)) {
                log.debug("Replacing file {}", filePath);
            }
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(filePath.toString()));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving file");
        }
    }
    public void downloadAudio(MultipartFile audioFile, String animeFolder, String audioName) {
        String audioFileName = audioName+getFileExtension(audioFile.getOriginalFilename());
        saveFile(audioFile,"audio",animeFolder,audioFileName);
    }

    public String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }
    private void handleFolderExisting(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            log.info("Папка для аниме {} успешно создана.", path.getFileName().toString());
        }
    }

}
