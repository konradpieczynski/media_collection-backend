package com.media_collection.backend.domain;

import com.media_collection.backend.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class MovieSuggester {

    @Autowired
    private final MovieService movieService;
    List<Movie> makeSuggestions(){
        List<Movie> suggestions = new ArrayList<>();
        List<Movie> movies = movieService.getMovies();
        if (!movies.isEmpty()) {
            Random random = new Random();
            for (int i = 0; i < 3; i++) {
                suggestions.add(movies.get(random.nextInt(movies.size())));
            }
        }
        return suggestions;
    }
}
