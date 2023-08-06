package com.media_collection.backend.mapper;

import com.media_collection.backend.domain.Movie;
import com.media_collection.backend.domain.MovieDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieMapper {
    public Movie mapToMovie(final MovieDto movieDto){
        return Movie.builder()
                .movieId(movieDto.getMovieId())
                .movieTitle(movieDto.getMovieTitle())
                .movieYear(movieDto.getMovieYear())
                .build();
    }

    public MovieDto mapToMovieDto(final Movie movie) {
        return MovieDto.builder()
                .movieId(movie.getMovieId())
                .movieTitle(movie.getMovieTitle())
                .movieYear(movie.getMovieYear())
                .build();
    }

    public List<MovieDto> mapToMovieDtoList(final List<Movie> movieList) {
        return movieList.stream()
                .map(this::mapToMovieDto)
                .toList();
    }
}
