package com.media_collection.backend.scheduler;

import com.media_collection.backend.domain.Movie;
import com.media_collection.backend.external_api.rapidapi.moviesdatabase.MoviesDatabaseClient;
import com.media_collection.backend.mapper.ExternalMovieMapper;
import com.media_collection.backend.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExternalApiMoviesScheduler {

    private final MovieService movieService;
    private final MoviesDatabaseClient moviesDatabaseClient;
    private final ExternalMovieMapper externalMovieMapper;
    @Scheduled(cron = "0 55 * * * *")
    public void saveExternalMovies(){
        log.info("\nSaving external movies to database\n");
        List<Movie> externalMoviesList = externalMovieMapper.mapToMovieList(moviesDatabaseClient.getExternalMovieDatabaseDto());
        List<Movie> movieList = movieService.getMovies();
        for (Movie movie: externalMoviesList) {
            if (!movieList.contains(movie)){
                movieService.saveMovie(movie);
            }
        }
        log.info("\nDone saving external movies to database\n");
    }
}
