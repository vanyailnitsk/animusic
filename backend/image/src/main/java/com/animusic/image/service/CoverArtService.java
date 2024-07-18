package com.animusic.image.service;

import com.animusic.core.db.model.CoverArt;
import com.animusic.core.db.model.Image;
import com.animusic.core.db.table.CoverArtRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CoverArtService {
    private final CoverArtRepository coverArtRepository;
    private final ImageService imageService;

    @Transactional
    public CoverArt createAlbumCoverArt(
            String animeFolder,
            String imageName,
            MultipartFile imageFile,
            CoverArt coverArt
    ) {
        Image image = imageService.createAnimeImage(animeFolder, imageName, imageFile);
        coverArt.setImage(image);
        return coverArtRepository.save(coverArt);
    }

    @Transactional
    public CoverArt createPlaylistCoverArt(
            Integer userId,
            String playlistName,
            MultipartFile imageFile,
            CoverArt coverArt
    ) {
        Image image = imageService.createImageForUser(userId, generateHash(playlistName, 10), imageFile);
        coverArt.setImage(image);
        return coverArtRepository.save(coverArt);
    }

    public static String generateHash(String input, int desiredLength) {
        String hash = DigestUtils.sha256Hex(input);
        return hash.substring(0, Math.min(desiredLength, hash.length()));
    }
}
