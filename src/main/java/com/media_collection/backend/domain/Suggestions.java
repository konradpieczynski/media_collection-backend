package com.media_collection.backend.domain;

public enum Suggestions {
    MOVIES("movies"),
    SONGS("songs");

    private final String value;
    Suggestions(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
