package com.media_collection.backend.domain;

import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonTypeName("movies")
public class MovieSuggestions implements SuggestionsInterface{
    @JsonTypeId
    private final String type = Suggestions.MOVIES.getValue();
    List<Movie> suggestedMovies;
    public MovieSuggestions(List<Movie> suggestedMedia) {
        this.suggestedMovies = suggestedMedia;
    }
}
