package com.media_collection.backend.service;

import com.media_collection.backend.controller.exceptions.MovieNotFoundException;
import com.media_collection.backend.domain.Movie;
import com.media_collection.backend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository repository;

    public Movie saveMovie(Movie Movie) {
        return repository.save(Movie);
    }

    public Movie findMovieById(Long MovieId) throws MovieNotFoundException {
        return repository.findById(MovieId).orElseThrow(MovieNotFoundException::new);
    }

    public List<Movie> getMovies() {
        return repository.findAll();
    }

    public void deleteById(Long MovieId) throws MovieNotFoundException {
        Movie toBeDeletedMovie = repository.findById(MovieId).orElseThrow(MovieNotFoundException::new);
        repository.delete(toBeDeletedMovie);
    }

    public List<Movie> findMovieByMovieName(String title) {
        return repository.findMoviesByMovieTitle(title);
    }
}
