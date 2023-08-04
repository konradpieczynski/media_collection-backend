package com.media_collection.backend.mapper;

import com.media_collection.backend.domain.Song;
import com.media_collection.backend.domain.SongDto;
import com.media_collection.backend.domain.SuggestionsFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class SongMapperTest {

    @Autowired
    SongMapper songMapper;
    @Autowired
    SuggestionsFactory suggestionsFactory;
    @Test
    void mapToSong() {
        //GIVEN
        SongDto songDto = new SongDto(1L, "Test song", "Test author");

        //WHEN
        Song song = songMapper.mapToSong(songDto);
        //THEN
        assertEquals(1L, song.getSongId());
        assertEquals("Test song", song.getSongTitle());
        assertEquals("Test author", song.getSongAuthor());
    }

    @Test
    void mapToSongDto() {
        //GIVEN
        Song song = new Song(1L, "Test song", "Test author",new ArrayList<>());

        //WHEN
        SongDto songDto = songMapper.mapToSongDto(song);
        //THEN
        assertEquals(1L, songDto.getSongId());
        assertEquals("Test song", songDto.getSongTitle());
        assertEquals("Test author", songDto.getSongAuthor());
    }

    @Test
    void mapToSongDtoList() {
        //GIVEN
        Song song1 = new Song();
        song1.setSongId(1L);
        song1.setSongTitle("Test song");
        song1.setSongAuthor("Test author");
        Song song2 = new Song();
        song2.setSongId(2L);
        song1.setSongTitle("Test song");
        song1.setSongAuthor("Test author");
        //WHEN
        List<SongDto> songDtoList = songMapper.mapToSongDtoList(List.of(song1,song2));
        //THEN
        assertEquals(2, songDtoList.size());
    }
}