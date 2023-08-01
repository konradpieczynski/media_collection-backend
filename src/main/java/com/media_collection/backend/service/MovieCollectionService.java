package com.media_collection.backend.service;

import com.media_collection.backend.controller.exceptions.MovieCollectionNotFoundException;
import com.media_collection.backend.domain.MovieCollection;
import com.media_collection.backend.repository.MovieCollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieCollectionService {
    private final MovieCollectionRepository repository;

    public MovieCollection saveMovieCollection(MovieCollection MovieCollection) {
        return repository.save(MovieCollection);
    }

    public MovieCollection findMovieCollectionById(Long MovieCollectionId) throws MovieCollectionNotFoundException {
        return repository.findById(MovieCollectionId).orElseThrow(MovieCollectionNotFoundException::new);
    }

    public List<MovieCollection> getMovieCollections() {
        return repository.findAll();
    }

    public void deleteById(Long MovieCollectionId) throws MovieCollectionNotFoundException {
        MovieCollection toBeDeletedMovieCollection = repository.findById(MovieCollectionId).orElseThrow(MovieCollectionNotFoundException::new);
        repository.delete(toBeDeletedMovieCollection);
    }

    public List<MovieCollection> findMovieCollectionByMovieCollectionName(String name) {
        return repository.findMovieCollectionsByName(name);
    }
}
