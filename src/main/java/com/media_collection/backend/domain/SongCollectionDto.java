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
public class SongCollectionDto {
    private Long songCollectionId;
    private Long userId;
    private String songCollectionName;
    @Builder.Default
    private Set<Long> songs = new HashSet<>();
}
