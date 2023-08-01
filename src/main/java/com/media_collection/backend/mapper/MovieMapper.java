package com.media_collection.backend.mapper;

import com.media_collection.backend.domain.Movie;
import com.media_collection.backend.domain.MovieDto;
import org.springframework.stereotype.Service;

@Service
public class MovieMapper {
    public Movie mapToMovie(final MovieDto movieDto){
        return Movie.builder()
                .movieId(movieDto.getMovieId())
                .title(movieDto.getMovieTitle())
                .build();
    }

    public MovieDto mapToMovieDto(final Movie movie) {
        return MovieDto.builder()
                .movieTitle(movie.getTitle())
                .build();
    }
}
