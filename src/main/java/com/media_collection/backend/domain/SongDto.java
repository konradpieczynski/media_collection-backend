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
public class SongDto {
    private Long songId;
    private String songTitle;
    private String songAuthor;
    @Builder.Default
    private Set<Long> songCollections = new HashSet<>();
}
