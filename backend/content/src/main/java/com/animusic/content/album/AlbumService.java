package com.animusic.content.album;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.animusic.content.anime.AnimeNotFoundException;
import com.animusic.content.anime.AnimeService;
import com.animusic.content.image.CoverArtService;
import com.animusic.core.db.model.Album;
import com.animusic.core.db.model.Anime;
import com.animusic.core.db.model.CoverArt;
import com.animusic.core.db.table.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AnimeService animeService;
    private final CoverArtService coverArtService;

    @Transactional
    public Album createAlbum(Album album, Integer animeId) {
        Anime anime = animeService.getAnime(animeId)
                .orElseThrow(() -> new AnimeNotFoundException(animeId));
        if (albumRepository.existsByNameAndAnimeId(album.getName(), animeId)) {
            throw new AlbumAlreadyExistsException(album.getName(), animeId);
        }
        album.setAnime(anime);
        albumRepository.save(album);
        return album;
    }

    public Optional<List<Album>> getAlbumsByAnimeId(Integer animeId) {
        var anime = animeService.getAnime(animeId);
        return anime.map(Anime::getAlbums);
    }

    public Album getAlbumById(Integer id) {
        Optional<Album> entity = albumRepository.findById(id);
        if (entity.isEmpty()) {
            throw new AlbumNotFoundException(id);
        }
        Album album = entity.get();
        String albumName = album.getName();
        if (albumName.equals("Openings") || albumName.equals("Endings")) {
            album.getSoundtracks().sort(Comparator.comparingInt(
                    a -> {
                        String title = a.getAnimeTitle();
                        if (title.matches(".*\\d+-\\d+.*")) {
                            // Формат "Ending 5-6", извлекаем оба номера и возвращаем их среднее
                            String[] parts = title.split("-");
                            int number1 = Integer.parseInt(parts[0].replaceAll("\\D+", ""));
                            int number2 = Integer.parseInt(parts[1].replaceAll("\\D+", ""));
                            return (number1 + number2) / 2;
                        } else {
                            // Стандартный формат, извлекаем номер
                            return Integer.parseInt(title.replaceAll("\\D+", ""));
                        }
                    }
            ));
        }
        return entity.get();
    }

    public void deleteAlbum(Integer id) {
        albumRepository.deleteById(id);
    }

    @Transactional
    public Album updateAlbumName(String newName, Integer albumId) {
        return albumRepository.findById(albumId).map(
                album -> {
                    album.setName(newName);
                    return album;
                }
        ).orElseThrow(() -> new AlbumNotFoundException(albumId));
    }

    @Transactional
    public CoverArt createCoverArt(Integer albumId, MultipartFile imageFile, String colorLight, String colorDark) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new AlbumNotFoundException(albumId));
        CoverArt coverArt = CoverArt.builder()
                .colorLight(colorLight)
                .colorDark(colorDark)
                .build();
        coverArt = coverArtService.createAlbumCoverArt(
                album.getAnime().getFolderName(),
                album.getName().toUpperCase(),
                imageFile,
                coverArt);
        album.setCoverArt(coverArt);
        return coverArt;
    }
}

