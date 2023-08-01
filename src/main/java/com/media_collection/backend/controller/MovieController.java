package com.media_collection.backend.controller;

import com.media_collection.backend.controller.exceptions.MovieNotFoundException;
import com.media_collection.backend.domain.Movie;
import com.media_collection.backend.domain.MovieDto;
import com.media_collection.backend.mapper.MovieMapper;
import com.media_collection.backend.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/movies")
@CrossOrigin("*")
@RequiredArgsConstructor
public class MovieController {
    private final MovieMapper movieMapper;
    private final MovieService movieService;

    @GetMapping(value = "{movieId}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Long movieId) throws MovieNotFoundException {
        return ResponseEntity.ok(movieMapper.mapToMovieDto(movieService.findMovieById(movieId)));
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getMovies() {
        return ResponseEntity.ok(movieMapper.mapToMovieDtoList(movieService.getMovies()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        movieService.saveMovie(movieMapper.mapToMovie(movieDto));
        return ResponseEntity.ok(movieDto);
    }

    @PutMapping
    public ResponseEntity<MovieDto> updateMovie(@RequestBody MovieDto movieDto) {
        Movie movie = movieMapper.mapToMovie(movieDto);
        Movie savedMovie = movieService.saveMovie(movie);
        return ResponseEntity.ok(movieMapper.mapToMovieDto(savedMovie));
    }

    @DeleteMapping(value = "{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId) throws MovieNotFoundException {
        movieService.deleteById(movieId);
        return ResponseEntity.ok().build();
    }
}