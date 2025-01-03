package animusic.api.mappers;

import org.springframework.stereotype.Component;

import animusic.api.dto.PlaylistOwnerDto;
import animusic.api.dto.UserDto;
import animusic.core.db.model.User;
import animusic.util.StoragePathResolver;

@Component
public class UserMapper {

    public static UserDto fromUser(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isEnabled(),
                user.getAuthorities()
        );
    }

    public static PlaylistOwnerDto playlistOwner(User user) {
        // Mocked until users will have avatar
        var avatarUrl = StoragePathResolver.getAbsoluteFileUrl("pain-avatar.jpeg");
        var avatar = new PlaylistOwnerDto.Avatar(avatarUrl);
        return new PlaylistOwnerDto(
                user.getId(),
                user.getUsername(),
                avatar
        );
    }
}
