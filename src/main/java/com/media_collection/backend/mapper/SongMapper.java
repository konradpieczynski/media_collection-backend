package com.media_collection.backend.mapper;

import com.media_collection.backend.controller.exceptions.SongCollectionNotFoundException;
import com.media_collection.backend.domain.Song;
import com.media_collection.backend.domain.SongCollection;
import com.media_collection.backend.domain.SongDto;
import com.media_collection.backend.service.SongCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongMapper {

    @Autowired
    SongCollectionService songCollectionService;

    public Song mapToSong(final SongDto songDto) {
        return Song.builder()
                .songId(songDto.getSongId())
                .songTitle(songDto.getSongTitle())
                .songAuthor(songDto.getSongAuthor())
                .songCollections(songDto.getSongCollections()
                        .stream()
                        .map(id -> {
                            try {
                                return songCollectionService.findSongCollectionById(id);
                            } catch (SongCollectionNotFoundException e) {
                                return null;
                            }
                        })
                        .collect(Collectors.toSet()))
                .build();
    }

    public SongDto mapToSongDto(final Song song) {
        return SongDto.builder()
                .songId(song.getSongId())
                .songTitle(song.getSongTitle())
                .songAuthor(song.getSongAuthor())
                .songCollections(song.getSongCollections()
                        .stream()
                        .map(SongCollection::getSongCollectionId)
                        .toList()
                )
                .build();
    }

    public List<SongDto> mapToSongDtoList(final List<Song> songList) {
        return songList.stream()
                .map(this::mapToSongDto)
                .toList();
    }
}
