package com.media_collection.backend.controller;

import com.media_collection.backend.controller.exceptions.MovieCollectionNotFoundException;
import com.media_collection.backend.domain.MovieCollection;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieCollectionDto> createMovieCollection(@RequestBody MovieCollectionDto movieCollectionDto) {
        movieCollectionService.saveMovieCollection(movieCollectionMapper.mapToMovieCollection(movieCollectionDto));
        return ResponseEntity.ok(movieCollectionDto);
    }

    @PutMapping
    public ResponseEntity<MovieCollectionDto> updateMovieCollection(@RequestBody MovieCollectionDto movieCollectionDto) {
        MovieCollection movieCollection = movieCollectionMapper.mapToMovieCollection(movieCollectionDto);
        MovieCollection savedMovieCollection = movieCollectionService.saveMovieCollection(movieCollection);
        return ResponseEntity.ok(movieCollectionMapper.mapToMovieCollectionDto(savedMovieCollection));
    }
    
    @DeleteMapping(value = "{movieCollectionId}")
    public ResponseEntity<Void> deleteMovieCollection(@PathVariable Long movieCollectionId) throws MovieCollectionNotFoundException {
        movieCollectionService.deleteById(movieCollectionId);
        return ResponseEntity.ok().build();
    }
}
