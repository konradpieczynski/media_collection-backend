package com.media_collection.backend.controller;

import com.media_collection.backend.controller.exceptions.MovieNotFoundException;
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

    @PostMapping(value ="/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createMovie(@RequestBody MovieDto movieDto) {
        movieService.saveMovie(movieMapper.mapToMovie(movieDto));
        return ResponseEntity.ok("New movie " + movieDto.getMovieTitle() + " was successfully created");
    }

    @DeleteMapping(value = "/delete/{movieId}")
    public ResponseEntity<Object> deleteMovie(@PathVariable Long movieId) throws MovieNotFoundException {
        String movieName = movieService.findMovieById(movieId).getTitle();
        movieService.deleteById(movieId);
        return ResponseEntity.ok("Movie " + movieName + " was successfully deleted");
    }
}