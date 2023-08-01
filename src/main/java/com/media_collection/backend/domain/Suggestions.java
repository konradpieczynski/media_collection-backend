package com.media_collection.backend.domain;

import lombok.Getter;

@Getter
public enum Suggestions {
    MOVIES("movies"),
    SONGS("songs");

    private final String value;
    Suggestions(String value) {
        this.value = value;
    }
}
