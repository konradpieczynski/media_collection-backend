package com.media_collection.backend.mapper;

import com.media_collection.backend.controller.exceptions.UserNotFoundException;
import com.media_collection.backend.domain.*;
import com.media_collection.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class SongCollectionMapperTest {

    @Autowired
    SongCollectionMapper songCollectionMapper;
    @Autowired
    SuggestionsFactory suggestionsFactory;

    @MockBean
    private UserService userService;
    @Test
    void mapToSongCollection() throws UserNotFoundException {
        //GIVEN
        SongCollectionDto songCollectionDto = new SongCollectionDto(1L, 1L, "Test songCollection", new ArrayList<>());
        User user = new User(1L,
                "Test user",
                Suggestions.SONGS,
                new HashSet<>(),
                new HashSet<>());
        when(userService.findUserById(songCollectionDto.getUserId())).thenReturn(user);
        //WHEN
        SongCollection songCollection = songCollectionMapper.mapToSongCollection(songCollectionDto);
        //THEN
        assertEquals(1L, songCollection.getSongCollectionId());
        assertEquals("Test user", songCollection.getUser().getUserName());
        assertEquals("Test songCollection", songCollection.getSongCollectionName());
    }

    @Test
    void mapToSongCollectionDto() {
        //GIVEN
        User user = new User(1L,
                "Test User",
                Suggestions.SONGS,
                new HashSet<>(),
                new HashSet<>());
        SongCollection songCollection = new SongCollection(1L, user, "Test songCollection",new HashSet<>());

        //WHEN
        SongCollectionDto songCollectionDto = songCollectionMapper.mapToSongCollectionDto(songCollection);
        //THEN
        assertEquals(1L, songCollectionDto.getSongCollectionId());
        assertEquals("Test songCollection", songCollectionDto.getSongCollectionName());
        assertEquals(1L, songCollectionDto.getUserId());
    }

    @Test
    void mapToSongCollectionDtoList() {
        //GIVEN
        User user = new User(1L,
                "Test User",
                Suggestions.SONGS,
                new HashSet<>(),
                new HashSet<>());
        SongCollection songCollection1 = new SongCollection();
        songCollection1.setSongCollectionId(1L);
        songCollection1.setSongCollectionName("Test songCollection");
        songCollection1.setUser(user);
        SongCollection songCollection2 = new SongCollection();
        songCollection2.setSongCollectionId(2L);
        songCollection2.setSongCollectionName("Test songCollection");
        songCollection2.setUser(user);
        //WHEN
        List<SongCollectionDto> songCollectionDtoList = songCollectionMapper.mapToSongCollectionDtoList(List.of(songCollection1,songCollection2));
        //THEN
        assertEquals(2, songCollectionDtoList.size());
    }
}