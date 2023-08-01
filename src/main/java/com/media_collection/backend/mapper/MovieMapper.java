package com.media_collection.backend.mapper;

import com.media_collection.backend.domain.Movie;
import com.media_collection.backend.domain.MovieDto;
import com.media_collection.backend.domain.Song;
import com.media_collection.backend.domain.SongDto;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<MovieDto> mapToMovieDtoList(final List<Movie> movieList) {
        return movieList.stream()
                .map(this::mapToMovieDto)
                .toList();
    }
}
