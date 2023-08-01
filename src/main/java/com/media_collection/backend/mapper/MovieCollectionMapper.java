package com.media_collection.backend.mapper;

import com.media_collection.backend.controller.exceptions.MovieNotFoundException;
import com.media_collection.backend.domain.Movie;
import com.media_collection.backend.domain.MovieCollection;
import com.media_collection.backend.domain.MovieCollectionDto;
import com.media_collection.backend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MovieCollectionMapper {

    @Autowired
    MovieService movieService;
    public MovieCollection mapToMovieCollection(final MovieCollectionDto movieCollectionDto) {
        return MovieCollection.builder()
                .name(movieCollectionDto.getMovieCollectionName())
                .movieList(movieCollectionDto.getMovieCollectionList()
                        .stream()
                        .map(movieId -> {
                            try {
                                return movieService.findMovieById(movieId);
                            } catch (MovieNotFoundException e) {
                                return null;
                            }
                        })
                        .collect(Collectors.toList()))
                .build();
    }

    public MovieCollectionDto mapToUserDto(final MovieCollection movieCollection) {
        return MovieCollectionDto.builder()
                .movieCollectionId(movieCollection.getMovieCollectionId())
                .movieCollectionName(movieCollection.getName())
                .movieCollectionList(movieCollection.getMovieList().stream().map(Movie::getMovieId).collect(Collectors.toList()))
                .build();
    }
}
