package com.media_collection.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MovieSuggestions implements SuggestionsInterface{
    private final String type = Suggestions.MOVIES.getValue();
    List<Movie> suggestedMovies;
    public MovieSuggestions(List<Movie> suggestedMedia) {
        this.suggestedMovies = suggestedMedia;
    }
}
