package com.media_collection.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Long movieId;
    private String movieTitle;
    private int movieYear;
    @Builder.Default
    private Set<Long> movieCollectionList = new HashSet<>();
}
