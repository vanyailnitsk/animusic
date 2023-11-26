package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.dto.AnimeNavDTO;
import com.ilnitsk.animusic.exception.AnimeNotFoundException;
import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.models.Anime;
import com.ilnitsk.animusic.repositories.AnimeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AnimeService {
    private final AnimeRepository animeRepository;
    private final ImageService imageService;
    @Value("${images.directory}")
    private String imagesPath;

    @Autowired
    public AnimeService(AnimeRepository animeRepository, ImageService imageService) {
        this.animeRepository = animeRepository;
        this.imageService = imageService;
    }

    public Anime getAnimeInfo(Integer animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        anime.getSoundtracks()
                .forEach(s -> s.setAnimeName(anime.getTitle()));
        return anime;
    }



    public List<AnimeNavDTO> getAnimeDropdownList() {
        List<Anime> animeList = animeRepository.findAll();
        List<AnimeNavDTO> animeDropdownList = new ArrayList<>();
        for (Anime anime : animeList) {
            AnimeNavDTO dto = new AnimeNavDTO(anime.getId(), anime.getTitle());
            animeDropdownList.add(dto);
        }

        return animeDropdownList;
    }

    public Anime createAnime(Anime anime, MultipartFile banner, MultipartFile card) {
        boolean existsTitle = animeRepository
                .existsAnimeByTitle(anime.getTitle());
        if (existsTitle) {
            throw new BadRequestException(
                    "Anime " + anime.getTitle() + " already exists"
            );
        }
        String animePath = anime.getFolderName() + "/";
        String bannerPath = animePath + "banner.jpeg";
        String cardPath = animePath + "card.jpeg";
        saveFile(banner, anime, "banner.jpeg");
        anime.setBannerImagePath(bannerPath);
        saveFile(card, anime, "card.jpeg");
        anime.setCardImagePath(cardPath);
        return animeRepository.save(anime);
    }
    public ResponseEntity<byte[]> getBanner(Integer animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        String bannerPath = anime.getBannerImagePath();
        if (bannerPath == null || bannerPath.isEmpty()) {
            return imageService.getImage("default-banner.jpg");
        }
        return imageService.getImage(anime.getBannerImagePath());
    }

    public void saveFile(MultipartFile file, Anime anime, String fileName) {
        try {
            Path folderPath = Paths.get(imagesPath, anime.getFolderName());
            handleFolderExisting(folderPath);
            Path absolutePath = Paths.get(imagesPath, anime.getFolderName(), fileName);
            if (Files.exists(absolutePath)) {
                log.error("File {} already exists", absolutePath);
                throw new BadRequestException("File " + absolutePath + " already exists");
            }
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(absolutePath.toString()));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving images");
        }
    }

    private static void handleFolderExisting(Path folderPath) throws IOException {
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
            log.info("Папка для аниме {} успешно создана.", folderPath.getFileName().toString());
        }
    }

    public List<Anime> getAllAnime() {
        return animeRepository.findAll();
    }
    public void deleteAnime(Integer animeId) {
        animeRepository.deleteById(animeId);
    }
}
