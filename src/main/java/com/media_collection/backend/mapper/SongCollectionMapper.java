package com.media_collection.backend.mapper;

import com.media_collection.backend.controller.exceptions.SongNotFoundException;
import com.media_collection.backend.controller.exceptions.UserNotFoundException;
import com.media_collection.backend.domain.*;
import com.media_collection.backend.service.SongService;
import com.media_collection.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongCollectionMapper {

    @Autowired
    SongService songService;
    @Autowired
    UserService userService;
    public SongCollection mapToSongCollection(final SongCollectionDto songCollectionDto) throws UserNotFoundException {
        return SongCollection.builder()
                .songCollectionId(songCollectionDto.getSongCollectionId())
                .songCollectionName(songCollectionDto.getSongCollectionName())
                .user(userService.findUserById(songCollectionDto.getUserId()))
                .songs(songCollectionDto.getSongs()
                        .stream()
                        .map(songId -> {
                            try {
                                return songService.findSongById(songId);
                            } catch (SongNotFoundException e) {
                                return null;
                            }
                        })
                        .collect(Collectors.toSet()))
                .build();
    }

    public SongCollectionDto mapToSongCollectionDto(final SongCollection songCollection) {
        return SongCollectionDto.builder()
                .songCollectionId(songCollection.getSongCollectionId())
                .userId(songCollection.getUser().getUserId())
                .songCollectionName(songCollection.getSongCollectionName())
                .songs(songCollection.getSongs().stream().map(Song::getSongId).collect(Collectors.toList()))
                .build();
    }

    public List<SongCollectionDto> mapToSongCollectionDtoList(final List<SongCollection> songCollectionList) {
        return songCollectionList.stream()
                .map(this::mapToSongCollectionDto)
                .toList();
    }
}
