package com.media_collection.backend.domain;

import org.springframework.stereotype.Component;
@Component
public class SuggestionsFactory {
    public final SuggestionsInterface makeSuggestions(final String suggestionType) {
        final String MOVIES = "movies";
        final String SONGS = "songs";

        return switch (suggestionType) {
            case MOVIES -> new MovieSuggestions();
            case SONGS -> new SongSuggestions();
            default -> null;
        };
    }
}
