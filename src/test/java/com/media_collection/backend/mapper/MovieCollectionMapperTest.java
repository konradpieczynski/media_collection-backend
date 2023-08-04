package com.media_collection.backend.mapper;

import com.media_collection.backend.domain.*;
import com.media_collection.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MovieCollectionMapperTest {

    @Autowired
    MovieCollectionMapper movieCollectionMapper;
    @Autowired
    SuggestionsFactory suggestionsFactory;

    @MockBean
    private UserService userService;
    @Test
    void mapToMovieCollection() {
        //GIVEN
        MovieCollectionDto movieCollectionDto = new MovieCollectionDto(1L, "Test user", "Test movieCollection", new ArrayList<>());
        User user = new User(1L,
                "Test user",
                Suggestions.MOVIES,
                new ArrayList<>(),
                new ArrayList<>());
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.findUserByUserName(movieCollectionDto.getUser())).thenReturn(userList);
        //WHEN
        MovieCollection movieCollection = movieCollectionMapper.mapToMovieCollection(movieCollectionDto);
        //THEN
        assertEquals(1L, movieCollection.getMovieCollectionId());
        assertEquals("Test user", movieCollection.getUser().getUserName());
        assertEquals("Test movieCollection", movieCollection.getMovieCollectionName());
    }

    @Test
    void mapToMovieCollectionDto() {
        //GIVEN
        User user = new User(1L,
                "Test User",
                Suggestions.MOVIES,
                new ArrayList<>(),
                new ArrayList<>());
        MovieCollection movieCollection = new MovieCollection(1L, user, "Test movieCollection",new ArrayList<>());

        //WHEN
        MovieCollectionDto movieCollectionDto = movieCollectionMapper.mapToMovieCollectionDto(movieCollection);
        //THEN
        assertEquals(1L, movieCollectionDto.getMovieCollectionId());
        assertEquals("Test movieCollection", movieCollectionDto.getMovieCollectionName());
        assertEquals("Test User", movieCollectionDto.getUser());
    }

    @Test
    void mapToMovieCollectionDtoList() {
        //GIVEN
        User user = new User(1L,
                "Test User",
                Suggestions.MOVIES,
                new ArrayList<>(),
                new ArrayList<>());
        MovieCollection movieCollection1 = new MovieCollection();
        movieCollection1.setMovieCollectionId(1L);
        movieCollection1.setMovieCollectionName("Test movieCollection");
        movieCollection1.setUser(user);
        MovieCollection movieCollection2 = new MovieCollection();
        movieCollection2.setMovieCollectionId(2L);
        movieCollection2.setMovieCollectionName("Test movieCollection");
        movieCollection2.setUser(user);
        //WHEN
        List<MovieCollectionDto> movieCollectionDtoList = movieCollectionMapper.mapToMovieCollectionDtoList(List.of(movieCollection1,movieCollection2));
        //THEN
        assertEquals(2, movieCollectionDtoList.size());
    }
}