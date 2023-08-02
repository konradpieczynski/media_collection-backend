package com.media_collection.backend.service;

import com.media_collection.backend.controller.exceptions.MovieCollectionNotFoundException;
import com.media_collection.backend.domain.MovieCollection;
import com.media_collection.backend.repository.MovieCollectionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
@Transactional
@SpringBootTest
class MovieCollectionServiceTest {

    @Autowired
    MovieCollectionService movieCollectionService;

    @Autowired
    MovieCollectionRepository movieCollectionRepository;

    @Test
    void saveMovieCollection() {
        //Given
        MovieCollection movieCollection = MovieCollection.builder()
                .name("Test movieCollection")
                .build();
        //When
        MovieCollection savedMovieCollection = movieCollectionService.saveMovieCollection(movieCollection);
        //Then
        assertEquals(movieCollection.getName(),savedMovieCollection.getName());
        assertEquals(1,movieCollectionService.findMovieCollectionByMovieCollectionName("Test movieCollection").size());
        movieCollectionRepository.deleteAll();
    }

    @Test
    void findMovieCollectionById() {
        //Given
        MovieCollection movieCollection = MovieCollection.builder()
                .movieCollectionId(1L)
                .name("Test movieCollection")
                .build();
        //When
        MovieCollection savedMovieCollection = movieCollectionService.saveMovieCollection(movieCollection);
        //Then
        assertEquals(movieCollection.getMovieCollectionId(),savedMovieCollection.getMovieCollectionId());
        try {
            assertEquals(1L, movieCollectionService.findMovieCollectionById(1L).getMovieCollectionId());
        } catch (MovieCollectionNotFoundException e) {
            fail();
        }
        movieCollectionRepository.deleteAll();
    }

    @Test
    void getMovieCollections() {
        //Given
        MovieCollection movieCollection = MovieCollection.builder()
                .name("Test movieCollection")
                .build();
        MovieCollection movieCollection2 = MovieCollection.builder()
                .name("Test movieCollection2")
                .build();
        //When
        movieCollectionService.saveMovieCollection(movieCollection);
        movieCollectionService.saveMovieCollection(movieCollection2);
        //Then
        assertEquals(2,movieCollectionRepository.count());
        movieCollectionRepository.deleteAll();
    }

    @Test
    void deleteById() {
        //Given
        MovieCollection movieCollection = MovieCollection.builder()
                .name("Test movieCollection")
                .movieCollectionId(1L)
                .build();
        MovieCollection movieCollection2 = MovieCollection.builder()
                .name("Test movieCollection2")
                .movieCollectionId(2L)
                .build();
        //When
        movieCollectionService.saveMovieCollection(movieCollection);
        movieCollectionService.saveMovieCollection(movieCollection2);
        try {
            movieCollectionService.deleteById(2L);
        } catch (MovieCollectionNotFoundException e) {
            fail();
        }
        //Then
        assertEquals(1,movieCollectionRepository.count());
        movieCollectionRepository.deleteAll();
    }

    @Test
    void findMovieCollectionByMovieCollectionName() {
        //Given
        MovieCollection movieCollection = MovieCollection.builder()
                .name("Test movieCollection")
                .movieCollectionId(1L)
                .build();
        MovieCollection movieCollection2 = MovieCollection.builder()
                .name("Test movieCollection2")
                .movieCollectionId(2L)
                .build();
        //When
        movieCollectionService.saveMovieCollection(movieCollection);
        movieCollectionService.saveMovieCollection(movieCollection2);
        List<MovieCollection> foundMovieCollections = movieCollectionService.findMovieCollectionByMovieCollectionName("Test movieCollection2");

        //Then
        assertEquals("Test movieCollection2", foundMovieCollections.get(0).getName());
        movieCollectionRepository.deleteAll();
    }
}