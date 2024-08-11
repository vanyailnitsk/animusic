package com.animusic.api.mappers;

import com.animusic.api.dto.PlaylistOwnerDto;
import com.animusic.api.dto.UserDto;
import com.animusic.core.db.model.User;
import com.animusic.s3.StoragePathResolver;
import org.springframework.stereotype.Component;

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
