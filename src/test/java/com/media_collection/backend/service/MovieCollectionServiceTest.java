package com.media_collection.backend.service;

import com.media_collection.backend.controller.exceptions.MovieCollectionNotFoundException;
import com.media_collection.backend.controller.exceptions.MovieCollectionNotFoundException;
import com.media_collection.backend.domain.MovieCollection;
import com.media_collection.backend.domain.MovieCollection;
import com.media_collection.backend.domain.User;
import com.media_collection.backend.repository.MovieCollectionRepository;
import com.media_collection.backend.repository.MovieCollectionRepository;
import com.media_collection.backend.repository.UserRepository;
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

    @Autowired
    UserRepository userRepository;

    @Test
    void saveMovieCollection() {
        //Given
        User user = userRepository.save(User.builder().userName("Test user").build());
        MovieCollection movieCollection = MovieCollection.builder()
                .movieCollectionId(1L)
                .user(user)
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
        User user = userRepository.save(User.builder().userName("Test user").build());
        MovieCollection movieCollection = MovieCollection.builder()
                .movieCollectionId(1L)
                .user(user)
                .name("Test movieCollection")
                .build();
        //When
        MovieCollection savedMovieCollection = movieCollectionService.saveMovieCollection(movieCollection);
        //Then
        try {
            assertEquals(savedMovieCollection.getMovieCollectionId(),
                    movieCollectionService.findMovieCollectionById(savedMovieCollection.getMovieCollectionId()).getMovieCollectionId());
        } catch (MovieCollectionNotFoundException e) {
            fail();
        }
        movieCollectionRepository.deleteAll();
    }

    @Test
    void getMovieCollections() {
        //Given
        User user = userRepository.save(User.builder().userName("Test user").build());
        MovieCollection movieCollection = MovieCollection.builder()
                .name("Test movieCollection")
                .user(user)
                .build();
        MovieCollection movieCollection2 = MovieCollection.builder()
                .name("Test movieCollection2")
                .user(user)
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
        User user = userRepository.save(User.builder().userName("Test user").build());
        MovieCollection movieCollection = MovieCollection.builder()
                .name("Test movieCollection")
                .user(user)
                .movieCollectionId(1L)
                .build();
        MovieCollection movieCollection2 = MovieCollection.builder()
                .name("Test movieCollection2")
                .user(user)
                .movieCollectionId(2L)
                .build();
        //When
        MovieCollection savedMovieCollection1 = movieCollectionService.saveMovieCollection(movieCollection);
        MovieCollection savedMovieCollection2 = movieCollectionService.saveMovieCollection(movieCollection2);
        try {
            movieCollectionService.deleteById(savedMovieCollection2.getMovieCollectionId());
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
        User user = userRepository.save(User.builder().userName("Test user").build());
        MovieCollection movieCollection = MovieCollection.builder()
                .name("Test movieCollection")
                .user(user)
                .movieCollectionId(1L)
                .build();
        MovieCollection movieCollection2 = MovieCollection.builder()
                .name("Test movieCollection2")
                .user(user)
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