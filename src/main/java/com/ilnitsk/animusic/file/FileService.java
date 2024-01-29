package com.ilnitsk.animusic.file;

import com.ilnitsk.animusic.exception.FileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    @Value("${images.directory}")
    private String imagesPath;
    @Value("${audiotracks.directory}")
    private String audioPath;
    public byte[] getFileContent(String path,String fileName) {
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

}
