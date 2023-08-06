package com.media_collection.backend.controller;

import com.google.gson.Gson;
import com.media_collection.backend.domain.Movie;
import com.media_collection.backend.domain.MovieDto;
import com.media_collection.backend.mapper.MovieMapper;
import com.media_collection.backend.service.MovieService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @SpyBean
    private MovieMapper movieMapper;

    @Test
    void shouldFetchEmptyMovieList() throws Exception {
        // Given
        when(movieService.getMovies()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchMovieList() throws Exception {
        // Given
        List<Movie> movies = List.of(new Movie(1L, "Test movie", 1999,new ArrayList<>()));
        when(movieService.getMovies()).thenReturn(movies);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].movieId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].movieTitle", Matchers.is("Test movie")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].movieYear", Matchers.is(1999)));
    }

    @Test
    void shouldFetchMovie() throws Exception {
        // Given
        Movie movie = new Movie(1L, "Test movie", 1999,new ArrayList<>());
        when(movieService.findMovieById(1L)).thenReturn(movie);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieTitle", Matchers.is("Test movie")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieYear", Matchers.is(1999)));
    }

    @Test
    void shouldDeleteMovie() throws Exception {
        //Given
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateMovie() throws Exception {
        // Given
        Movie movie = new Movie(1L, "Test movie", 1999,new ArrayList<>());
        MovieDto movieDto = new MovieDto(1L, "Test movie", 1999);
        when(movieService.saveMovie(any(Movie.class))).thenReturn(movie);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(movieDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieTitle", Matchers.is("Test movie")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieYear", Matchers.is(1999)));
    }

    @Test
    void shouldCreateMovie() throws Exception {
        // Given
        MovieDto movieDto = new MovieDto(1L, "Test movie", 1999);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(movieDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}