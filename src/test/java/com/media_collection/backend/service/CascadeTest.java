package com.media_collection.backend.service;

import com.media_collection.backend.controller.exceptions.SongCollectionNotFoundException;
import com.media_collection.backend.controller.exceptions.SongNotFoundException;
import com.media_collection.backend.domain.Song;
import com.media_collection.backend.domain.SongCollection;
import com.media_collection.backend.domain.User;
import com.media_collection.backend.repository.SongCollectionRepository;
import com.media_collection.backend.repository.SongRepository;
import com.media_collection.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Transactional
@SpringBootTest
public class CascadeTest {
    @Autowired
    SongService songService;

    @Autowired
    SongCollectionService songCollectionService;

    @Autowired
    SongRepository songRepository;

    @Autowired
    SongCollectionRepository songCollectionRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void cascadeDeleteSong() throws SongNotFoundException {

        //Given
        Song song = Song.builder()
                .songTitle("Test song")
                .songAuthor("a1")
                .songId(1L)
                .build();
        Song song2 = Song.builder()
                .songTitle("Test song2")
                .songAuthor("a2")
                .songId(2L)
                .build();
        User user = userRepository.save(User.builder().userName("Test user").build());
        SongCollection songCollection = SongCollection.builder()
                .songCollectionName("Test songCollection")
                .user(user)
                .build();
        SongCollection songCollection2 = SongCollection.builder()
                .songCollectionName("Test songCollection2")
                .user(user)
                .build();
        Song savedSong = songService.saveSong(song);
        Song savedSong2 = songService.saveSong(song2);
        songCollection.getSongs().add(savedSong);
        songCollection.getSongs().add(savedSong2);
        songCollection2.getSongs().add(savedSong);
        songCollection2.getSongs().add(savedSong2);
        SongCollection songCollection1 = songCollectionService.saveSongCollection(songCollection);
        SongCollection songCollection3 = songCollectionService.saveSongCollection(songCollection2);
        //When
        songService.deleteById(savedSong.getSongId());
        //Then
        assertEquals(1, songService.getSongs().size());
        assertEquals(2, songCollectionService.getSongCollections().size());
        assertEquals(1, userRepository.count());
        songRepository.deleteAll();
        songCollectionRepository.deleteAll();
    }

    @Test
    void cascadeDeleteSongCollection() throws SongCollectionNotFoundException {

        //Given
        Song song = Song.builder()
                .songTitle("Test song")
                .songAuthor("a1")
                .songId(1L)
                .build();
        Song song2 = Song.builder()
                .songTitle("Test song2")
                .songAuthor("a2")
                .songId(2L)
                .build();
        User user = userRepository.save(User.builder().userName("Test user").build());
        SongCollection songCollection = SongCollection.builder()
                .songCollectionName("Test songCollection")
                .user(user)
                .build();
        SongCollection songCollection2 = SongCollection.builder()
                .songCollectionName("Test songCollection2")
                .user(user)
                .build();
        Song savedSong = songService.saveSong(song);
        Song savedSong2 = songService.saveSong(song2);
        songCollection.getSongs().add(savedSong);
        songCollection.getSongs().add(savedSong2);
        songCollection2.getSongs().add(savedSong);
        songCollection2.getSongs().add(savedSong2);
        SongCollection songCollection1 = songCollectionService.saveSongCollection(songCollection);
        SongCollection songCollection3 = songCollectionService.saveSongCollection(songCollection2);
        //When
        songCollectionService.deleteById(songCollection1.getSongCollectionId());
        //Then
        assertEquals(2, songService.getSongs().size());
        assertEquals(1, songCollectionService.getSongCollections().size());
        assertEquals(1, userRepository.count());
        songRepository.deleteAll();
        songCollectionRepository.deleteAll();
    }
    @Test
    void cascadeDeleteUser() throws SongCollectionNotFoundException {

        //Given
        Song song = Song.builder()
                .songTitle("Test song")
                .songAuthor("a1")
                .songId(1L)
                .build();
        Song song2 = Song.builder()
                .songTitle("Test song2")
                .songAuthor("a2")
                .songId(2L)
                .build();
        User user = userRepository.save(User.builder().userName("Test user").build());
        SongCollection songCollection = SongCollection.builder()
                .songCollectionName("Test songCollection")
                .user(user)
                .build();
        SongCollection songCollection2 = SongCollection.builder()
                .songCollectionName("Test songCollection2")
                .user(user)
                .build();
        Song savedSong = songService.saveSong(song);
        Song savedSong2 = songService.saveSong(song2);
        songCollection.getSongs().add(savedSong);
        songCollection.getSongs().add(savedSong2);
        songCollection2.getSongs().add(savedSong);
        songCollection2.getSongs().add(savedSong2);
        SongCollection songCollection1 = songCollectionService.saveSongCollection(songCollection);
        SongCollection songCollection3 = songCollectionService.saveSongCollection(songCollection2);
        //When
        userRepository.deleteAll();
        //Then
        assertEquals(2, songService.getSongs().size());
        assertEquals(2, songCollectionService.getSongCollections().size());
        assertEquals(0, userRepository.count());
        songRepository.deleteAll();
        songCollectionRepository.deleteAll();
    }
}
