package com.media_collection.backend.mapper;

import com.media_collection.backend.domain.Song;
import com.media_collection.backend.domain.SongDto;
import com.media_collection.backend.domain.Song;
import com.media_collection.backend.domain.SongDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongMapper {
    public Song mapToSong(final SongDto songDto){
        return Song.builder()
                .songId(songDto.getSongId())
                .title(songDto.getSongTitle())
                .build();
    }

    public SongDto mapToSongDto(final Song song) {
        return SongDto.builder()
                .songTitle(song.getTitle())
                .build();
    }

    public List<SongDto> mapToSongDtoList(final List<Song> songList) {
        return songList.stream()
                .map(this::mapToSongDto)
                .toList();
    }
}
