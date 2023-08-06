package com.media_collection.backend.repository;

import com.media_collection.backend.domain.MovieCollection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieCollectionRepository extends CrudRepository<MovieCollection, Long> {
    @Override
    List<MovieCollection> findAll();

    List<MovieCollection> findMovieCollectionsByMovieCollectionName(String name);
}