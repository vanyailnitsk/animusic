package com.animusic.api.mappers;

import com.animusic.api.dto.PlaylistOwnerDto;
import com.animusic.api.dto.UserDto;
import com.animusic.core.db.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto fromUser(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isEnabled(),
                user.getAuthorities()
        );
    }

    public PlaylistOwnerDto playlistOwner(User user) {
        // Mocked until users will have avatar
        var avatar = new PlaylistOwnerDto.Avatar("pain-avatar.jpeg");
        return new PlaylistOwnerDto(
                user.getId(),
                user.getUsername(),
                avatar
        );
    }
}
