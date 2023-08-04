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
public class MovieCollectionDto {
    private Long movieCollectionId;
    private String user;
    private String movieCollectionName;
    private List<Long> movieCollectionList = new ArrayList<>();
}
