package com.media_collection.backend.controller;

import com.media_collection.backend.controller.exceptions.MovieCollectionNotFoundException;
import com.media_collection.backend.domain.MovieCollectionDto;
import com.media_collection.backend.mapper.MovieCollectionMapper;
import com.media_collection.backend.service.MovieCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/movieCollections")
@CrossOrigin("*")
@RequiredArgsConstructor
public class MovieCollectionController {
    private final MovieCollectionMapper movieCollectionMapper;
    private final MovieCollectionService movieCollectionService;

    @GetMapping(value = "{movieCollectionId}")
    public ResponseEntity<MovieCollectionDto> getMovieCollection(@PathVariable Long movieCollectionId) throws MovieCollectionNotFoundException {
        return ResponseEntity.ok(movieCollectionMapper.mapToMovieCollectionDto(movieCollectionService.findMovieCollectionById(movieCollectionId)));
    }

    @GetMapping
    public ResponseEntity<List<MovieCollectionDto>> getMovieCollections() {
        return ResponseEntity.ok(movieCollectionMapper.mapToMovieCollectionDtoList(movieCollectionService.getMovieCollections()));
    }

    @PostMapping(value ="/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createMovieCollection(@RequestBody MovieCollectionDto movieCollectionDto) {
        movieCollectionService.saveMovieCollection(movieCollectionMapper.mapToMovieCollection(movieCollectionDto));
        return ResponseEntity.ok("New movieCollection " + movieCollectionDto.getMovieCollectionName() + " was successfully created");
    }

    @DeleteMapping(value = "/delete/{movieCollectionId}")
    public ResponseEntity<Object> deleteMovieCollection(@PathVariable Long movieCollectionId) throws MovieCollectionNotFoundException {
        String movieCollectionName = movieCollectionService.findMovieCollectionById(movieCollectionId).getName();
        movieCollectionService.deleteById(movieCollectionId);
        return ResponseEntity.ok("MovieCollection " + movieCollectionName + " was successfully deleted");
    }
}
