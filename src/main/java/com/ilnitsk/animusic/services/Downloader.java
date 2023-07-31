package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.models.Anime;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Downloader {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String url = scanner.next();
        String outputDir = "/Users/admin/Music/animusic-audio";
        String fileName = "Promised_Neverland_Opening1";
    }
    public static int downloadAudioFromUrl(String url,Path path,String fileName) {
        handleFolderExisting(path);
        try {
            String outputFileName = fileName+".mp3";
            List<String> commands = List.of(
                    "youtube-dl", "-x", "--audio-format", "mp3","--output",outputFileName, url);
            ProcessBuilder processBuilder = new ProcessBuilder(commands);
            processBuilder.directory(path.toFile());
            Process process = processBuilder.start();
            System.out.println("Downloading started: "+fileName);
            process.waitFor();
            System.out.println(outputFileName + " has been successfully downloaded.");
            return 0;
        } catch (IOException | InterruptedException e) {
            return 1;
        }
    }
    public static int downloadAudioFromFile(MultipartFile file,Path path,String fileName) {
        handleFolderExisting(path);
        String newFilePath = Paths.get(path.toString(),fileName+".mp3").toString();
        try {
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFilePath));
            return 0;
        } catch (IOException e) {
            return 1;
        }
    }
    public static void handleFolderExisting(Path folderPath) {
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectories(folderPath);
                System.out.println("Папка для аниме " + folderPath.getFileName().toString() + " успешно создана.");
            } catch (IOException e) {
                System.err.println("Ошибка при создании папки для аниме " + folderPath.getFileName().toString() + ": " + e.getMessage());
            }
        }
    }
}

