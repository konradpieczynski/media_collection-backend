package com.media_collection.backend.repository;

import com.media_collection.backend.domain.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    @Override
    List<Movie> findAll();

    List<Movie> findMoviesByMovieTitle(String title);
}