package com.media_collection.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String userName;
    private SuggestionsInterface suggestions;
    @Builder.Default
    private Set<Long> movieCollectionList = new HashSet<>();
    @Builder.Default
    private Set<Long> songCollectionList = new HashSet<>();
}
