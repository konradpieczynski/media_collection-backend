package com.media_collection.backend.domain;

import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@JsonTypeName("movies")
public class MovieSuggestions implements SuggestionsInterface{
    @JsonTypeId
    private final String type = Suggestions.MOVIES.getValue();
    Set<Movie> suggestedMovies;
    public MovieSuggestions(Set<Movie> suggestedMedia) {
        this.suggestedMovies = suggestedMedia;
    }
}
