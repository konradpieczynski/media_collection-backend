package com.media_collection.backend.scheduler;

import com.media_collection.backend.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StatisticsScheduler {

    private final UserService userService;
    private final MovieService movieService;
    private final MovieCollectionService movieCollectionService;
    private final SongService songService;
    private final SongCollectionService songCollectionService;

    @Scheduled(cron = "0 0 0 * * *")
    public void sendStatistics(){
        log.info(
                "Statistics:\n"
                + "Users: " + userService.getUsers().size() + "\n"
                        + "Movies: " + movieService.getMovies().size() + "\n"
                        + "Movie collections: " + movieCollectionService.getMovieCollections().size() + "\n"
                        + "Songs: " + songService.getSongs().size() + "\n"
                        + "Song collections: " + songCollectionService.getSongCollections().size() + "\n"
                );
    }
}
