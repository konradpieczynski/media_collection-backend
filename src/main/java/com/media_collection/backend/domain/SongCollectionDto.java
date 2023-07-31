package com.media_collection.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SongCollectionDto {
    private Long songCollectionId;
    private String songCollectionName;
    private List<Long> songCollectionList = new ArrayList<>();
}
