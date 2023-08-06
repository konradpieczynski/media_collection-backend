package com.media_collection.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SongSuggestions implements SuggestionsInterface{
    private final String type = Suggestions.SONGS.getValue();
    List<Song> suggestedSongs;

    public SongSuggestions(List<Song> suggestedSongs) {
        this.suggestedSongs = suggestedSongs;
    }
}
