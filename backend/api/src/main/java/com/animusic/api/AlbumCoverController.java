package com.animusic.api;

import com.animusic.api.dto.CoverArtDto;
import com.animusic.content.album.AlbumService;
import com.animusic.core.db.model.CoverArt;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/albums/cover-art")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "REST API для управления обложкой альбома")
public class AlbumCoverController {

    private final AlbumService albumService;

    @PostMapping("{albumId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CoverArtDto createAlbumCover(
            @PathVariable Integer albumId,
            @RequestPart(value = "imageFile") MultipartFile imageFile,
            @ModelAttribute CreateCoverDto coverArtDto
    ) {
        CoverArt coverArt = albumService.createCoverArt(
                albumId,
                imageFile,
                coverArtDto.colorLight(),
                coverArtDto.colorDark()
        );
        return CoverArtDto.fromCoverArt(coverArt);
    }

    public record CreateCoverDto(String colorLight, String colorDark) {
    }
}
