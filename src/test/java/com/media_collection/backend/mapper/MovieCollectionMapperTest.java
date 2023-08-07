package com.media_collection.backend.mapper;

import com.media_collection.backend.controller.exceptions.UserNotFoundException;
import com.media_collection.backend.domain.*;
import com.media_collection.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
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
    void mapToMovieCollection() throws UserNotFoundException {
        //GIVEN
        MovieCollectionDto movieCollectionDto = new MovieCollectionDto(1L, 1L, "Test movieCollection", new HashSet<>());
        User user = new User(1L,
                "Test user",
                Suggestions.MOVIES,
                new HashSet<>(),
                new HashSet<>());
        when(userService.findUserById(movieCollectionDto.getUserId())).thenReturn(user);
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
                new HashSet<>(),
                new HashSet<>());
        MovieCollection movieCollection = new MovieCollection(1L, user, "Test movieCollection",new HashSet<>());

        //WHEN
        MovieCollectionDto movieCollectionDto = movieCollectionMapper.mapToMovieCollectionDto(movieCollection);
        //THEN
        assertEquals(1L, movieCollectionDto.getMovieCollectionId());
        assertEquals("Test movieCollection", movieCollectionDto.getMovieCollectionName());
        assertEquals(1L, movieCollectionDto.getUserId());
    }

    @Test
    void mapToMovieCollectionDtoList() {
        //GIVEN
        User user = new User(1L,
                "Test User",
                Suggestions.MOVIES,
                new HashSet<>(),
                new HashSet<>());
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