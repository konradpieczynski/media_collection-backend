package com.media_collection.backend.mapper;

import com.media_collection.backend.domain.User;
import com.media_collection.backend.domain.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;
    @Test
    void mapToUser() {
        UserDto userDto = new UserDto(1L, "title", "content");
        //WHEN
        User user = userMapper.mapToUser(userDto);
        //THEN
        assertEquals(1L, user.getId());
        assertEquals("title", user.getTitle());
        assertEquals("content", user.getContent());
    }

    @Test
    void mapToUserDto() {
    }

    @Test
    void mapToUserDtoList() {
    }
}