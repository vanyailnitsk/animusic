package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.models.Anime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class Downloader {
    public static void downloadAudio(Object audioSource, Path path, String fileName) throws IOException, InterruptedException {
        if (Files.exists(Paths.get(path.toString(), fileName + ".mp3"))) {
            log.error("File {} already exists",fileName);
            throw new IOException();
        }
        if (audioSource instanceof MultipartFile) {
            downloadAudioFromFile((MultipartFile) audioSource, path, fileName);
        } else if (audioSource instanceof String) {
            downloadAudioFromYoutube((String) audioSource, path, fileName);
        } else {
            throw new IllegalArgumentException("Unsupported audio source type");
        }
    }

    private static void downloadAudioFromYoutube(String url, Path path, String fileName) throws IOException, InterruptedException {
        handleFolderExisting(path);
        String outputFileName = fileName + ".mp3";
        List<String> commands = List.of(
                "yt-dlp", "-x", "--audio-format", "mp3", "--output", outputFileName, url);
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.directory(path.toFile());
        Process process = processBuilder.start();
        log.info("Downloading started: {}", fileName);
        process.waitFor();
        log.info("{} has been successfully downloaded.", outputFileName);
    }

    private static void downloadAudioFromFile(MultipartFile file, Path path, String fileName) throws IOException {
        handleFolderExisting(path);
        String newFilePath = Paths.get(path.toString(), fileName + ".mp3").toString();
        FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFilePath));
    }

    private static void handleFolderExisting(Path folderPath) throws IOException {
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
            log.info("Папка для аниме {} успешно создана.", folderPath.getFileName().toString());
        }
    }
}

