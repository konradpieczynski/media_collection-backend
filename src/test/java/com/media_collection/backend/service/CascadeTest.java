package com.media_collection.backend.service;

import com.media_collection.backend.controller.exceptions.*;
import com.media_collection.backend.domain.*;
import com.media_collection.backend.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class CascadeTest {
    @Autowired
    SongService songService;

    @Autowired
    SongCollectionService songCollectionService;

    @Autowired
    SongRepository songRepository;

    @Autowired
    SongCollectionRepository songCollectionRepository;

    @Autowired
    MovieService movieService;

    @Autowired
    MovieCollectionService movieCollectionService;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieCollectionRepository movieCollectionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @AfterEach
    public void afterEachTest(){
        songRepository.deleteAll();
        songCollectionRepository.deleteAll();
        movieRepository.deleteAll();
        movieCollectionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void cascadeDeleteSong() throws SongNotFoundException {

        //Given
        Song song = Song.builder()
                .songTitle("Test song")
                .songAuthor("test author")
                .songId(1L)
                .build();
        Song song2 = Song.builder()
                .songTitle("Test song2")
                .songAuthor("test author")
                .songId(2L)
                .build();
        User user = userRepository.save(User.builder().userName("Test user").build());
        SongCollection songCollection = SongCollection.builder()
                .songCollectionName("Test songCollection")
                .user(user)
                .build();
        SongCollection songCollection2 = SongCollection.builder()
                .songCollectionName("Test songCollection2")
                .user(user)
                .build();
        Song savedSong = songService.saveSong(song);
        Song savedSong2 = songService.saveSong(song2);

        SongCollection songCollection1 = songCollectionService.saveSongCollection(songCollection);
        SongCollection songCollection3 = songCollectionService.saveSongCollection(songCollection2);

        songCollection1.getSongs().add(savedSong);
        songCollection1.getSongs().add(savedSong2);
        songCollection3.getSongs().add(savedSong);
        songCollection3.getSongs().add(savedSong2);

        songCollectionService.saveSongCollection(songCollection1);
        songCollectionService.saveSongCollection(songCollection3);

        //When
        songService.deleteById(savedSong.getSongId());
        //Then
        assertEquals(1, songService.getSongs().size());
        assertEquals(2, songCollectionService.getSongCollections().size());
        assertEquals(1, userService.getUsers().size());
    }

    @Test
    void cascadeDeleteSongCollection() throws SongCollectionNotFoundException {

        //Given
        Song song = Song.builder()
                .songTitle("Test song")
                .songAuthor("test author")
                .songId(1L)
                .build();
        Song song2 = Song.builder()
                .songTitle("Test song2")
                .songAuthor("test author")
                .songId(2L)
                .build();
        User user = userRepository.save(User.builder().userName("Test user").build());
        SongCollection songCollection = SongCollection.builder()
                .songCollectionName("Test songCollection")
                .user(user)
                .build();
        SongCollection songCollection2 = SongCollection.builder()
                .songCollectionName("Test songCollection2")
                .user(user)
                .build();
        Song savedSong = songService.saveSong(song);
        Song savedSong2 = songService.saveSong(song2);

        SongCollection songCollection1 = songCollectionService.saveSongCollection(songCollection);
        SongCollection songCollection3 = songCollectionService.saveSongCollection(songCollection2);

        songCollection1.getSongs().add(savedSong);
        songCollection1.getSongs().add(savedSong2);
        songCollection3.getSongs().add(savedSong);
        songCollection3.getSongs().add(savedSong2);

        songCollectionService.saveSongCollection(songCollection1);
        SongCollection songCollection5 = songCollectionService.saveSongCollection(songCollection3);
        //When
        songCollectionService.deleteById(songCollection5.getSongCollectionId());
        //Then
        assertEquals(2, songService.getSongs().size());
        assertEquals(1, songCollectionService.getSongCollections().size());
        assertEquals(1, userService.getUsers().size());
    }
    @Test
    void cascadeDeleteUserWithSongCollections() throws UserNotFoundException {

        //Given
        Song song = Song.builder()
                .songTitle("Test song")
                .songAuthor("test author")
                .songId(1L)
                .build();
        Song song2 = Song.builder()
                .songTitle("Test song2")
                .songAuthor("test author")
                .songId(2L)
                .build();
        User user = userRepository.save(User.builder().userName("Test user").build());
        SongCollection songCollection = SongCollection.builder()
                .songCollectionName("Test songCollection")
                .user(user)
                .build();
        SongCollection songCollection2 = SongCollection.builder()
                .songCollectionName("Test songCollection2")
                .user(user)
                .build();
        Song savedSong = songService.saveSong(song);
        Song savedSong2 = songService.saveSong(song2);

        SongCollection songCollection1 = songCollectionService.saveSongCollection(songCollection);
        SongCollection songCollection3 = songCollectionService.saveSongCollection(songCollection2);

        songCollection1.getSongs().add(savedSong);
        songCollection1.getSongs().add(savedSong2);
        songCollection3.getSongs().add(savedSong);
        songCollection3.getSongs().add(savedSong2);

        songCollectionService.saveSongCollection(songCollection1);
        SongCollection songCollection5 = songCollectionService.saveSongCollection(songCollection3);

        //When
        userService.deleteById(user.getUserId());
        //Then
        assertEquals(2, songService.getSongs().size());
        assertEquals(0, songCollectionService.getSongCollections().size());
        assertEquals(0, userService.getUsers().size());
    }
    @Test
    void cascadeDeleteMovie() throws MovieNotFoundException {

        //Given
        Movie movie = Movie.builder()
                .movieTitle("Test movie")
                .movieYear(1999)
                .movieId(1L)
                .build();
        Movie movie2 = Movie.builder()
                .movieTitle("Test movie2")
                .movieYear(1989)
                .movieId(2L)
                .build();
        User user = userRepository.save(User.builder().userName("Test user").build());
        MovieCollection movieCollection = MovieCollection.builder()
                .movieCollectionName("Test movieCollection")
                .user(user)
                .build();
        MovieCollection movieCollection2 = MovieCollection.builder()
                .movieCollectionName("Test movieCollection2")
                .user(user)
                .build();
        Movie savedMovie = movieService.saveMovie(movie);
        Movie savedMovie2 = movieService.saveMovie(movie2);

        MovieCollection movieCollection1 = movieCollectionService.saveMovieCollection(movieCollection);
        MovieCollection movieCollection3 = movieCollectionService.saveMovieCollection(movieCollection2);

        movieCollection1.getMovies().add(savedMovie);
        movieCollection1.getMovies().add(savedMovie2);
        movieCollection3.getMovies().add(savedMovie);
        movieCollection3.getMovies().add(savedMovie2);

        movieCollectionService.saveMovieCollection(movieCollection1);
        movieCollectionService.saveMovieCollection(movieCollection3);

        //When
        movieService.deleteById(savedMovie.getMovieId());
        //Then
        assertEquals(1, movieService.getMovies().size());
        assertEquals(2, movieCollectionService.getMovieCollections().size());
        assertEquals(1, userService.getUsers().size());
    }

    @Test
    void cascadeDeleteMovieCollection() throws MovieCollectionNotFoundException {

        //Given
        Movie movie = Movie.builder()
                .movieTitle("Test movie")
                .movieYear(1999)
                .movieId(1L)
                .build();
        Movie movie2 = Movie.builder()
                .movieTitle("Test movie2")
                .movieYear(1989)
                .movieId(2L)
                .build();
        User user = userRepository.save(User.builder().userName("Test user").build());
        MovieCollection movieCollection = MovieCollection.builder()
                .movieCollectionName("Test movieCollection")
                .user(user)
                .build();
        MovieCollection movieCollection2 = MovieCollection.builder()
                .movieCollectionName("Test movieCollection2")
                .user(user)
                .build();
        Movie savedMovie = movieService.saveMovie(movie);
        Movie savedMovie2 = movieService.saveMovie(movie2);

        MovieCollection movieCollection1 = movieCollectionService.saveMovieCollection(movieCollection);
        MovieCollection movieCollection3 = movieCollectionService.saveMovieCollection(movieCollection2);

        movieCollection1.getMovies().add(savedMovie);
        movieCollection1.getMovies().add(savedMovie2);
        movieCollection3.getMovies().add(savedMovie);
        movieCollection3.getMovies().add(savedMovie2);

        movieCollectionService.saveMovieCollection(movieCollection1);
        MovieCollection movieCollection5 = movieCollectionService.saveMovieCollection(movieCollection3);
        //When
        movieCollectionService.deleteById(movieCollection5.getMovieCollectionId());
        //Then
        assertEquals(2, movieService.getMovies().size());
        assertEquals(1, movieCollectionService.getMovieCollections().size());
        assertEquals(1, userService.getUsers().size());
    }
    @Test
    void cascadeDeleteUserWithMovieCollections() throws UserNotFoundException {

        //Given
        Movie movie = Movie.builder()
                .movieTitle("Test movie")
                .movieYear(1999)
                .movieId(1L)
                .build();
        Movie movie2 = Movie.builder()
                .movieTitle("Test movie2")
                .movieYear(1989)
                .movieId(2L)
                .build();
        User user = userRepository.save(User.builder().userName("Test user").build());
        MovieCollection movieCollection = MovieCollection.builder()
                .movieCollectionName("Test movieCollection")
                .user(user)
                .build();
        MovieCollection movieCollection2 = MovieCollection.builder()
                .movieCollectionName("Test movieCollection2")
                .user(user)
                .build();
        Movie savedMovie = movieService.saveMovie(movie);
        Movie savedMovie2 = movieService.saveMovie(movie2);

        MovieCollection movieCollection1 = movieCollectionService.saveMovieCollection(movieCollection);
        MovieCollection movieCollection3 = movieCollectionService.saveMovieCollection(movieCollection2);

        movieCollection1.getMovies().add(savedMovie);
        movieCollection1.getMovies().add(savedMovie2);
        movieCollection3.getMovies().add(savedMovie);
        movieCollection3.getMovies().add(savedMovie2);

        movieCollectionService.saveMovieCollection(movieCollection1);
        movieCollectionService.saveMovieCollection(movieCollection3);

        //When
        userService.deleteById(user.getUserId());
        //Then
        assertEquals(2, movieService.getMovies().size());
        assertEquals(0, movieCollectionService.getMovieCollections().size());
        assertEquals(0, userService.getUsers().size());
    }

}
