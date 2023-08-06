package com.media_collection.backend.mapper;

import com.media_collection.backend.domain.Movie;
import com.media_collection.backend.domain.MovieDto;
import com.media_collection.backend.domain.SuggestionsFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MovieMapperTest {

    @Autowired
    MovieMapper movieMapper;
    @Autowired
    SuggestionsFactory suggestionsFactory;
    @Test
    void mapToMovie() {
        //GIVEN
        MovieDto movieDto = new MovieDto(1L, "Test movie", 1999);

        //WHEN
        Movie movie = movieMapper.mapToMovie(movieDto);
        //THEN
        assertEquals(1L, movie.getMovieId());
        assertEquals("Test movie", movie.getMovieTitle());
        assertEquals(1999, movie.getMovieYear());
    }

    @Test
    void mapToMovieDto() {
        //GIVEN
        Movie movie = new Movie(1L, "Test movie", 1999,new ArrayList<>());

        //WHEN
        MovieDto movieDto = movieMapper.mapToMovieDto(movie);
        //THEN
        assertEquals(1L, movieDto.getMovieId());
        assertEquals("Test movie", movieDto.getMovieTitle());
        assertEquals(1999, movieDto.getMovieYear());
    }

    @Test
    void mapToMovieDtoList() {
        //GIVEN
        Movie movie1 = new Movie();
        movie1.setMovieId(1L);
        movie1.setMovieTitle("Test movie");
        movie1.setMovieYear(1999);
        Movie movie2 = new Movie();
        movie2.setMovieId(2L);
        movie1.setMovieTitle("Test movie");
        movie1.setMovieYear(1999);
        //WHEN
        List<MovieDto> movieDtoList = movieMapper.mapToMovieDtoList(List.of(movie1,movie2));
        //THEN
        assertEquals(2, movieDtoList.size());
    }
}