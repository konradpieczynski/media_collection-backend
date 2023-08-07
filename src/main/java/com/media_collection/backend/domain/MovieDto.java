package com.media_collection.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Long movieId;
    private String movieTitle;
    private int movieYear;
    private List<Long> movieCollectionList = new ArrayList<>();
}
