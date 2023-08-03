package com.media_collection.backend.service;

import com.media_collection.backend.controller.exceptions.MovieNotFoundException;
import com.media_collection.backend.domain.Movie;
import com.media_collection.backend.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
@Transactional
@SpringBootTest
class MovieServiceTest {

    @Autowired
    MovieService movieService;

    @Autowired
    MovieRepository movieRepository;
    @Test
    void saveMovie() {
        //Given
        Movie movie = Movie.builder()
                .movieTitle("Test movie")
                .build();
        //When
        Movie savedMovie = movieService.saveMovie(movie);
        //Then
        assertEquals(movie.getMovieTitle(),savedMovie.getMovieTitle());
        assertEquals(1,movieService.findMovieByMovieName("Test movie").size());
        movieRepository.deleteAll();
    }

    @Test
    void findMovieById() {
        //Given
        Movie movie = Movie.builder()
                .movieId(1L)
                .movieTitle("Test movie")
                .build();
        //When
        Movie savedMovie = movieService.saveMovie(movie);
        //Then
        try {
            assertEquals(savedMovie.getMovieId(), movieService.findMovieById(savedMovie.getMovieId()).getMovieId());
        } catch (MovieNotFoundException e) {
            fail();
        }
        movieRepository.deleteAll();
    }

    @Test
    void getMovies() {
        //Given
        Movie movie = Movie.builder()
                .movieTitle("Test movie")
                .build();
        Movie movie2 = Movie.builder()
                .movieTitle("Test movie2")
                .build();
        //When
        movieService.saveMovie(movie);
        movieService.saveMovie(movie2);
        //Then
        assertEquals(2,movieService.getMovies().size());
        movieRepository.deleteAll();
    }

    @Test
    void deleteById() {
        //Given
        Movie movie = Movie.builder()
                .movieTitle("Test movie")
                .movieId(1L)
                .build();
        Movie movie2 = Movie.builder()
                .movieTitle("Test movie2")
                .movieId(2L)
                .build();
        //When
        movieService.saveMovie(movie);
        Movie savedMovie = movieService.saveMovie(movie2);
        try {
            movieService.deleteById(savedMovie.getMovieId());
        } catch (MovieNotFoundException e) {
            fail();
        }
        //Then
        assertEquals(1,movieRepository.count());
        movieRepository.deleteAll();
    }

    @Test
    void findMovieByMovieName() {
        //Given
        Movie movie = Movie.builder()
                .movieTitle("Test movie")
                .movieId(1L)
                .build();
        Movie movie2 = Movie.builder()
                .movieTitle("Test movie2")
                .movieId(2L)
                .build();
        //When
        movieService.saveMovie(movie);
        movieService.saveMovie(movie2);
        List<Movie> foundMovies = movieService.findMovieByMovieName("Test movie2");

        //Then
        assertEquals("Test movie2", foundMovies.get(0).getMovieTitle());
        movieRepository.deleteAll();
    }
}