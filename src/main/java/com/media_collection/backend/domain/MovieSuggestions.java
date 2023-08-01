package com.media_collection.backend.domain;

import lombok.Getter;

@Getter
public class MovieSuggestions implements SuggestionsInterface{
    private final String type = Suggestions.MOVIES.getValue();
}
