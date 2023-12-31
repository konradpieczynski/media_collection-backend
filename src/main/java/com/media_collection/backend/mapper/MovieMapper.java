package com.media_collection.backend.mapper;

import com.media_collection.backend.controller.exceptions.MovieCollectionNotFoundException;
import com.media_collection.backend.domain.Movie;
import com.media_collection.backend.domain.MovieDto;
import com.media_collection.backend.domain.MovieCollection;
import com.media_collection.backend.service.MovieCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieMapper {

    @Autowired
    MovieCollectionService movieCollectionService;
    
    public Movie mapToMovie(final MovieDto movieDto){
        return Movie.builder()
                .movieId(movieDto.getMovieId())
                .movieTitle(movieDto.getMovieTitle())
                .movieYear(movieDto.getMovieYear())
                .movieCollections(movieDto.getMovieCollections()
                        .stream()
                        .map(id -> {
                            try {
                                return movieCollectionService.findMovieCollectionById(id);
                            } catch (MovieCollectionNotFoundException e) {
                                return null;
                            }
                        })
                        .collect(Collectors.toSet()))
                .build();
    }

    public MovieDto mapToMovieDto(final Movie movie) {
        return MovieDto.builder()
                .movieId(movie.getMovieId())
                .movieTitle(movie.getMovieTitle())
                .movieYear(movie.getMovieYear())
                .movieCollections(movie.getMovieCollections()
                        .stream()
                        .map(MovieCollection::getMovieCollectionId)
                        .collect(Collectors.toSet())
                )
                .build();
    }

    public List<MovieDto> mapToMovieDtoList(final List<Movie> movieList) {
        return movieList.stream()
                .map(this::mapToMovieDto)
                .toList();
    }
}
