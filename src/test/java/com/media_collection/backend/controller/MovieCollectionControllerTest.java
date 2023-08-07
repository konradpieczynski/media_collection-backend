package com.media_collection.backend.controller;

import com.google.gson.Gson;
import com.media_collection.backend.domain.MovieCollection;
import com.media_collection.backend.domain.MovieCollectionDto;
import com.media_collection.backend.domain.Suggestions;
import com.media_collection.backend.domain.User;
import com.media_collection.backend.mapper.MovieCollectionMapper;
import com.media_collection.backend.service.MovieCollectionService;
import com.media_collection.backend.service.MovieService;
import com.media_collection.backend.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(MovieCollectionController.class)
class MovieCollectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieCollectionService movieCollectionService;

    @MockBean
    private MovieService movieService;

    @MockBean
    private UserService userService;

    @SpyBean
    private MovieCollectionMapper movieCollectionMapper;

    @Test
    void shouldFetchEmptyMovieCollectionList() throws Exception {
        // Given
        when(movieCollectionService.getMovieCollections()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/movieCollections")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchMovieCollectionList() throws Exception {
        // Given
        User user = new User(1L,
                "Test User",
                Suggestions.MOVIES,
                new HashSet<>(),
                new HashSet<>());
        MovieCollection movieCollection = new MovieCollection(1L, user, "Test movieCollection",new HashSet<>());
        List<MovieCollection> movieCollections = List.of(movieCollection);
        when(movieCollectionService.getMovieCollections()).thenReturn(movieCollections);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/movieCollections")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].movieCollectionId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].movieCollectionName", Matchers.is("Test movieCollection")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].movieCollections", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchMovieCollection() throws Exception {
        // Given
        User user = new User(1L,
                "Test User",
                Suggestions.MOVIES,
                new HashSet<>(),
                new HashSet<>());
        MovieCollection movieCollection = new MovieCollection(1L, user, "Test movieCollection",new HashSet<>());
        when(movieCollectionService.findMovieCollectionById(1L)).thenReturn(movieCollection);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/movieCollections/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieCollectionId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieCollectionName", Matchers.is("Test movieCollection")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieCollections", Matchers.hasSize(0)));
    }

    @Test
    void shouldDeleteMovieCollection() throws Exception {
        //Given
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/movieCollections/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateMovieCollection() throws Exception {
        // Given
        User user = new User(1L,
                "Test User",
                Suggestions.MOVIES,
                new HashSet<>(),
                new HashSet<>());
        MovieCollection movieCollection = new MovieCollection(1L, user, "Test movieCollection",new HashSet<>());
        MovieCollectionDto movieCollectionDto = new MovieCollectionDto(1L, 1L, "Test movieCollection", new ArrayList<>());
        when(userService.findUserById(movieCollectionDto.getUserId())).thenReturn(user);
        when(movieCollectionService.saveMovieCollection(any(MovieCollection.class))).thenReturn(movieCollection);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(movieCollectionDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/movieCollections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieCollectionId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieCollectionName", Matchers.is("Test movieCollection")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieCollections", Matchers.hasSize(0)));
    }

    @Test
    void shouldCreateMovieCollection() throws Exception {
        // Given
        User user = new User(1L,
                "Test User",
                Suggestions.MOVIES,
                new HashSet<>(),
                new HashSet<>());
        MovieCollectionDto movieCollectionDto = new MovieCollectionDto(1L, 1L, "Test movieCollection", new ArrayList<>());
        when(userService.findUserById(movieCollectionDto.getUserId())).thenReturn(user);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(movieCollectionDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/movieCollections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}