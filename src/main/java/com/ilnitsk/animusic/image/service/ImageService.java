package com.ilnitsk.animusic.image.service;

import com.ilnitsk.animusic.file.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ImageService {
    private final FileService fileService;

    public ResponseEntity<byte[]> getImage(String animeFolder,String fileName) {
        byte[] imageBytes = fileService.getImageContent(animeFolder,fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/webp"))
                .body(imageBytes);
    }
    public void saveImage(MultipartFile file,String animeFolder, String fileName) {
        fileService.saveImage(file,animeFolder,fileName);
    }

    public String getImageExtension(String fileName) {
        return fileService.getFileExtension(fileName);
    }
    public ResponseEntity<byte[]> getDefaultBanner() {
        byte[] imageBytes = fileService.getImageContent("","default-banner.webp");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/webp"))
                .body(imageBytes);
    }
    public ResponseEntity<byte[]> getDefaultCard() {
        byte[] imageBytes = fileService.getImageContent("","default-card.webp");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/webp"))
                .body(imageBytes);
    }

    public ResponseEntity<byte[]> getDefaultSoundtrackImage() {
        byte[] imageBytes = fileService.getImageContent("","track-img.webp");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/webp"))
                .body(imageBytes);
    }

}
