package com.media_collection.backend.mapper;

import com.media_collection.backend.domain.User;
import com.media_collection.backend.domain.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapper {
    public User mapToUser(final UserDto userDto){
        return User.builder()
                .userId(userDto.getUserId())
                .userName(userDto.getUserName())
                .build();
    }

    public UserDto mapToUserDto(final User user) {
        return UserDto.builder()
                .userName(user.getUserName())
                .build();
    }
    public List<UserDto> mapToUserDtoList(final List<User> userList) {
        return userList.stream()
                .map(this::mapToUserDto)
                .toList();
    }
}
