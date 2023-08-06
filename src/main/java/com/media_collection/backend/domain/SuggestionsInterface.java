package com.media_collection.backend.domain;

import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SongSuggestions.class, name = "songs"),
        @JsonSubTypes.Type(value = MovieSuggestions.class, name = "movies")
})
public interface SuggestionsInterface {
    String type = null;
    String getType();
}
