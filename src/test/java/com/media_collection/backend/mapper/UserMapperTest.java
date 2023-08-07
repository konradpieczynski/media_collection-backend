package com.media_collection.backend.mapper;

import com.media_collection.backend.domain.Suggestions;
import com.media_collection.backend.domain.SuggestionsFactory;
import com.media_collection.backend.domain.User;
import com.media_collection.backend.domain.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;
    @Autowired
    SuggestionsFactory suggestionsFactory;
    @Test
    void mapToUser() {
        //GIVEN
        UserDto userDto = new UserDto(1L,
                "Test user",
                suggestionsFactory.makeSuggestions("songs"),
                new HashSet<>(),
                new HashSet<>());

        //WHEN
        User user = userMapper.mapToUser(userDto);
        //THEN
        assertEquals(1L, user.getUserId());
        assertEquals("songs", user.getSuggestions().getValue());
        assertEquals("Test user", user.getUserName());
        assertEquals(0, user.getMovieCollectionList().size());
        assertEquals(0, user.getSongCollectionList().size());
    }

    @Test
    void mapToUserDto() {
        //GIVEN
        User user = new User(1L,
                "Test UserDto",
                Suggestions.SONGS,
                new HashSet<>(),
                new HashSet<>());

        //WHEN
        UserDto userDto = userMapper.mapToUserDto(user);
        //THEN
        assertEquals(1L, userDto.getUserId());
        assertEquals("songs", userDto.getSuggestions().getType());
        assertEquals("Test UserDto", userDto.getUserName());
        assertEquals(0, userDto.getMovieCollectionList().size());
        assertEquals(0, userDto.getSongCollectionList().size());
    }

    @Test
    void mapToUserDtoList() {
        //GIVEN
        User user1 = new User();
        user1.setUserId(1L);
        user1.setUserName("Test user");
        user1.setSuggestions(Suggestions.MOVIES);
        user1.setMovieCollectionList(new HashSet<>());
        user1.setSongCollectionList(new HashSet<>());
        User user2 = new User();
        user2.setUserId(2L);
        user2.setUserName("Test user2");
        user2.setSuggestions(Suggestions.MOVIES);
        user2.setMovieCollectionList(new HashSet<>());
        user2.setSongCollectionList(new HashSet<>());
        //WHEN
        List<UserDto> userDtoList = userMapper.mapToUserDtoList(List.of(user1,user2));
        //THEN
        assertEquals(2, userDtoList.size());
    }
}