package com.media_collection.backend.repository;

import com.media_collection.backend.domain.MovieCollection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieCollectionRepository extends CrudRepository<MovieCollection, Long> {

    @Override
    MovieCollection save(MovieCollection MovieCollection);

    @Override
    Optional<MovieCollection> findById(Long MovieCollectionId);

    @Override
    List<MovieCollection> findAll();

    @Override
    void deleteById(Long MovieCollectionId);

    List<MovieCollection> findMovieCollectionsByMovieCollectionName(String name);
}