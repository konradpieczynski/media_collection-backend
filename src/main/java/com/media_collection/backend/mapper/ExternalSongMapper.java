package com.media_collection.backend.mapper;

import com.media_collection.backend.domain.AppleSongResponseDto;
import com.media_collection.backend.domain.Song;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExternalSongMapper {
    public List<Song> mapToSongList(AppleSongResponseDto databaseDto) {
        return databaseDto.getFeed().getResults().stream()
                .map(result -> Song.builder()
                        .songTitle(result.getName())
                        .songAuthor(result.getArtistName())
                        .build())
                .collect(Collectors.toList());
    }
}
