package com.media_collection.backend.service;

import com.media_collection.backend.controller.exceptions.SongNotFoundException;
import com.media_collection.backend.domain.Song;
import com.media_collection.backend.repository.SongRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
@Transactional
@SpringBootTest
class SongServiceTest {

    @Autowired
    SongService songService;

    @Autowired
    SongRepository songRepository;
    @Test
    void saveSong() {
        //Given
        Song song = Song.builder()
                .title("Test song")
                .build();
        //When
        Song savedSong = songService.saveSong(song);
        //Then
        assertEquals(song.getTitle(),savedSong.getTitle());
        assertEquals(1,songService.findSongBySongName("Test song").size());
        songRepository.deleteAll();
    }

    @Test
    void findSongById() {
        //Given
        Song song = Song.builder()
                .songId(1L)
                .title("Test song")
                .build();
        //When
        Song savedSong = songService.saveSong(song);
        //Then
        assertEquals(song.getSongId(),savedSong.getSongId());
        try {
            assertEquals(1L, songService.findSongById(1L).getSongId());
        } catch (SongNotFoundException e) {
            fail();
        }
        songRepository.deleteAll();
    }

    @Test
    void getSongs() {
        //Given
        Song song = Song.builder()
                .title("Test song")
                .build();
        Song song2 = Song.builder()
                .title("Test song2")
                .build();
        //When
        songService.saveSong(song);
        songService.saveSong(song2);
        //Then
        assertEquals(2,songRepository.count());
        songRepository.deleteAll();
    }

    @Test
    void deleteById() {
        //Given
        Song song = Song.builder()
                .title("Test song")
                .songId(1L)
                .build();
        Song song2 = Song.builder()
                .title("Test song2")
                .songId(2L)
                .build();
        //When
        songService.saveSong(song);
        songService.saveSong(song2);
        try {
            songService.deleteById(2L);
        } catch (SongNotFoundException e) {
            fail();
        }
        //Then
        assertEquals(1,songRepository.count());
        songRepository.deleteAll();
    }

    @Test
    void findSongBySongName() {
        //Given
        Song song = Song.builder()
                .title("Test song")
                .songId(1L)
                .build();
        Song song2 = Song.builder()
                .title("Test song2")
                .songId(2L)
                .build();
        //When
        songService.saveSong(song);
        songService.saveSong(song2);
        List<Song> foundSongs = songService.findSongBySongName("Test song2");

        //Then
        assertEquals("Test song2", foundSongs.get(0).getTitle());
        songRepository.deleteAll();
    }
}