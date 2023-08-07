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
public class SongDto {
    private Long songId;
    private String songTitle;
    private String songAuthor;
    private List<Long> songCollectionList = new ArrayList<>();
}
