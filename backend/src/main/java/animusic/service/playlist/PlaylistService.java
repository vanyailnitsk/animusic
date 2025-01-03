package animusic.service.playlist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import animusic.core.db.model.CoverArt;
import animusic.core.db.model.Playlist;
import animusic.core.db.model.User;
import animusic.core.db.table.PlaylistRepository;
import animusic.service.image.CoverArtService;
import animusic.service.security.UserService;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final UserService userService;

    private final CoverArtService coverArtService;

    @Transactional
    public Playlist createPlaylist(String playlistName) {
        User user = userService.getUserInSession().orElseThrow(() -> new RuntimeException("User not found in session"));
        Playlist playlist = Playlist.builder()
                .name(playlistName)
                .user(user)
                .build();
        return playlistRepository.save(playlist);
    }

    public Playlist getPlaylistById(Integer playlistId) {
        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistId));
    }

    @Transactional
    public CoverArt createCoverArt(Integer playlistId, MultipartFile imageFile, CoverArt coverArt) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistId));
        CoverArt newCoverArt = coverArtService.createPlaylistCoverArt(
                playlist.getUser().getId(),
                playlist.getName(),
                imageFile,
                coverArt);
        playlist.setCoverArt(newCoverArt);
        return newCoverArt;
    }
}
