package com.media_collection.backend.domain;

import lombok.Getter;

@Getter
public class SongSuggestions implements SuggestionsInterface{
    private final String type = Suggestions.SONGS.getValue();
}
