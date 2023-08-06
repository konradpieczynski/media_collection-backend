package com.media_collection.backend.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class SuggestionsFactory {
    private final SongSuggester songSuggester;
    private final MovieSuggester movieSuggester;
    public final SuggestionsInterface makeSuggestions(final String suggestionType) {
        final String MOVIES = "movies";
        final String SONGS = "songs";

        return switch (suggestionType) {
            case MOVIES -> new MovieSuggestions(movieSuggester.makeSuggestions());
            case SONGS -> new SongSuggestions(songSuggester.makeSuggestions());
            default -> null;
        };
    }
}
