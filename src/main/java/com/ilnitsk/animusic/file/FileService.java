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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileService {
    @Value("${storage.directory}")
    private String storagePath;

    public byte[] getFileBytes(String animeFolder, String subDirectory, String fileName) {
        Path filePath = Paths.get(storagePath, animeFolder, subDirectory, fileName);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(filePath.toString());
        }
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file: %s".formatted(filePath.toString()));
        }
    }

    public void saveFile(MultipartFile file, String subDirectory, String animeFolder, String fileName) {
        try {
            Path folderPath = Paths.get(storagePath, animeFolder, subDirectory);
            handleFolderExisting(folderPath);
            Path filePath = Paths.get(folderPath.toString(), fileName);
            if (Files.exists(filePath)) {
                log.debug("Replacing file {}", filePath);
            }
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(filePath.toString()));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving file");
        }
    }

    private void handleFolderExisting(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            log.info("Папка {} успешно создана.", path.toAbsolutePath());
        }
    }

    public String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public void saveImage(MultipartFile imageFile, String animeFolder, String imageName) {
        String imageFileName = imageName + getFileExtension(imageFile.getOriginalFilename());
        saveFile(imageFile, "images", animeFolder, imageFileName);
    }

    public byte[] getImageContent(String animeFolder, String fileName) {
        return getFileBytes(animeFolder, "images", fileName);
    }

    public void saveAudio(MultipartFile audioFile, String animeFolder, String audioName) {
        String audioFileName = audioName + getFileExtension(audioFile.getOriginalFilename());
        saveFile(audioFile, "audio", animeFolder, audioFileName);
    }

    public byte[] getAudioContent(String animeFolder, String audioFileName, int rangeStart, int rangeEnd) {
        Path audioPath = Paths.get(storagePath, animeFolder, "audio", audioFileName);
        if (!Files.exists(audioPath)) {
            throw new FileNotFoundException(audioPath.toString());
        }
        try (InputStream inputStream = Files.newInputStream(audioPath)) {
            inputStream.skip(rangeStart);
            return inputStream.readNBytes(rangeEnd - rangeStart + 1);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка во время чтения аудиофайла");
        }
    }

    public int getAudioFileSize(String animeFolder, String audioFileName) {
        Path audioPath = Paths.get(storagePath, animeFolder, "audio", audioFileName);
        try {
            return (int) Files.size(audioPath);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка во время чтения аудиофайла");
        }
    }

    public void removeFile(String animeFolder, String subDirectory, String fileName) {
        try {
            Path file = Paths.get(storagePath, animeFolder, subDirectory, fileName);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка во время удаления файла");
        }
    }
}
