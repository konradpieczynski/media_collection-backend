package com.media_collection.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieCollectionDto {
    private Long movieCollectionId;
    private String movieCollectionName;
    private List<Long> movieCollectionList = new ArrayList<>();
}
