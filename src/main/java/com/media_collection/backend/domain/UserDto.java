package com.media_collection.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String userName;
    private Serializable suggestions;
    private List<Long> movieCollectionList = new ArrayList<>();
    private List<Long> songCollectionList = new ArrayList<>();
}
