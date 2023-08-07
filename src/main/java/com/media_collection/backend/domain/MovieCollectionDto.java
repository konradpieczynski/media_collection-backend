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
public class MovieCollectionDto {
    private Long movieCollectionId;
    private Long userId;
    private String movieCollectionName;
    @Builder.Default
    private Set<Long> movies = new HashSet<>();
}
