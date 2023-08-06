package com.media_collection.backend.mapper;

import com.media_collection.backend.domain.ExternalMovieDatabaseDto;
import com.media_collection.backend.domain.Movie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExternalMovieMapper {
    public List<Movie> mapToMovieList(ExternalMovieDatabaseDto databaseDto) {
        return databaseDto.getResults().stream()
                .map(result -> Movie.builder()
                        .movieTitle(result.getOriginalTitleTextDto().getText())
                        .movieYear(result.getReleaseYear().getYear())
                        .build())
                .collect(Collectors.toList());
    }
}
