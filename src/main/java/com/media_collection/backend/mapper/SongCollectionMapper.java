package com.media_collection.backend.mapper;

import com.media_collection.backend.controller.exceptions.SongNotFoundException;
import com.media_collection.backend.domain.*;
import com.media_collection.backend.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongCollectionMapper {

    @Autowired
    SongService songService;
    public SongCollection mapToSongCollection(final SongCollectionDto songCollectionDto) {
        return SongCollection.builder()
                .songCollectionId(songCollectionDto.getSongCollectionId())
                .name(songCollectionDto.getSongCollectionName())
                .songList(songCollectionDto.getSongCollectionList()
                        .stream()
                        .map(songId -> {
                            try {
                                return songService.findSongById(songId);
                            } catch (SongNotFoundException e) {
                                return null;
                            }
                        })
                        .collect(Collectors.toList()))
                .build();
    }

    public SongCollectionDto mapToSongCollectionDto(final SongCollection songCollection) {
        return SongCollectionDto.builder()
                .songCollectionId(songCollection.getSongCollectionId())
                .songCollectionName(songCollection.getName())
                .songCollectionList(songCollection.getSongList().stream().map(Song::getSongId).collect(Collectors.toList()))
                .build();
    }

    public List<SongCollectionDto> mapToSongCollectionDtoList(final List<SongCollection> songCollectionList) {
        return songCollectionList.stream()
                .map(this::mapToSongCollectionDto)
                .toList();
    }
}
