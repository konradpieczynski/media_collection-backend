package com.media_collection.backend.domain;

import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonTypeName("songs")
public class SongSuggestions implements SuggestionsInterface{
    @JsonTypeId
    private final String type = Suggestions.SONGS.getValue();
    List<Song> suggestedSongs;

    public SongSuggestions(List<Song> suggestedSongs) {
        this.suggestedSongs = suggestedSongs;
    }
}
