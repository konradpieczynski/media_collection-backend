package com.media_collection.backend.service;

import com.media_collection.backend.controller.exceptions.SongNotFoundException;
import com.media_collection.backend.domain.Song;
import com.media_collection.backend.repository.SongRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
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
                .songTitle("Test song")
                .build();
        //When
        Song savedSong = songService.saveSong(song);
        //Then
        assertEquals(song.getSongTitle(),savedSong.getSongTitle());
        assertEquals(1,songService.findSongBySongName("Test song").size());
        songRepository.deleteAll();
    }

    @Test
    void findSongById() {
        //Given
        Song song = Song.builder()
                .songId(1L)
                .songTitle("Test song")
                .build();
        //When
        Song savedSong = songService.saveSong(song);
        //Then
        try {
            assertEquals(savedSong.getSongId(), songService.findSongById(savedSong.getSongId()).getSongId());
        } catch (SongNotFoundException e) {
            fail();
        }
        songRepository.deleteAll();
    }

    @Test
    void getSongs() {
        //Given
        Song song = Song.builder()
                .songTitle("Test song")
                .build();
        Song song2 = Song.builder()
                .songTitle("Test song2")
                .build();
        //When
        songService.saveSong(song);
        songService.saveSong(song2);
        //Then
        assertEquals(2,songService.getSongs().size());
        songRepository.deleteAll();
    }

    @Test
    void deleteById() {
        //Given
        Song song = Song.builder()
                .songTitle("Test song")
                .songId(1L)
                .build();
        Song song2 = Song.builder()
                .songTitle("Test song2")
                .songId(2L)
                .build();
        //When
        songService.saveSong(song);
        Song savedSong = songService.saveSong(song2);
        try {
            songService.deleteById(savedSong.getSongId());
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
                .songTitle("Test song")
                .songId(1L)
                .build();
        Song song2 = Song.builder()
                .songTitle("Test song2")
                .songId(2L)
                .build();
        //When
        songService.saveSong(song);
        songService.saveSong(song2);
        List<Song> foundSongs = songService.findSongBySongName("Test song2");

        //Then
        assertEquals("Test song2", foundSongs.get(0).getSongTitle());
        songRepository.deleteAll();
    }
}