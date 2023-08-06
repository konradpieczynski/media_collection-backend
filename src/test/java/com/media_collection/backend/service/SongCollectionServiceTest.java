package com.media_collection.backend.service;

import com.media_collection.backend.controller.exceptions.SongCollectionNotFoundException;
import com.media_collection.backend.domain.SongCollection;
import com.media_collection.backend.domain.User;
import com.media_collection.backend.repository.SongCollectionRepository;
import com.media_collection.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
@Transactional
@SpringBootTest
class SongCollectionServiceTest {

    @Autowired
    SongCollectionService songCollectionService;

    @Autowired
    SongCollectionRepository songCollectionRepository;

    @Autowired
    UserRepository userRepository;
    
    @Test
    void saveSongCollection() {
        //Given
        User user = userRepository.save(User.builder().userName("Test user").build());
        SongCollection songCollection = SongCollection.builder()
                .songCollectionId(1L)
                .user(user)
                .songCollectionName("Test songCollection")
                .build();
        //When
        SongCollection savedSongCollection = songCollectionService.saveSongCollection(songCollection);
        //Then
        assertEquals(songCollection.getSongCollectionName(),savedSongCollection.getSongCollectionName());
        assertEquals(1,songCollectionService.findSongCollectionBySongCollectionName("Test songCollection").size());
        songCollectionRepository.deleteAll();
    }

    @Test
    void findSongCollectionById() {
        //Given
        User user = userRepository.save(User.builder().userName("Test user").build());
        SongCollection songCollection = SongCollection.builder()
                .songCollectionId(1L)
                .user(user)
                .songCollectionName("Test songCollection")
                .build();
        //When
        SongCollection savedSongCollection = songCollectionService.saveSongCollection(songCollection);
        //Then
        try {
            assertEquals(savedSongCollection.getSongCollectionId(),
                    songCollectionService.findSongCollectionById(savedSongCollection.getSongCollectionId()).getSongCollectionId());
        } catch (SongCollectionNotFoundException e) {
            fail();
        }
        songCollectionRepository.deleteAll();
    }

    @Test
    void getSongCollections() {
        //Given
        User user = userRepository.save(User.builder().userName("Test user").build());
        SongCollection songCollection = SongCollection.builder()
                .songCollectionName("Test songCollection")
                .user(user)
                .build();
        SongCollection songCollection2 = SongCollection.builder()
                .songCollectionName("Test songCollection2")
                .user(user)
                .build();
        //When
        songCollectionService.saveSongCollection(songCollection);
        songCollectionService.saveSongCollection(songCollection2);
        //Then
        assertEquals(2,songCollectionService.getSongCollections().size());
        songCollectionRepository.deleteAll();
    }

    @Test
    void deleteById() {
        //Given
        User user = userRepository.save(User.builder().userName("Test user").build());
        SongCollection songCollection = SongCollection.builder()
                .songCollectionName("Test songCollection")
                .user(user)
                .songCollectionId(1L)
                .build();
        SongCollection songCollection2 = SongCollection.builder()
                .songCollectionName("Test songCollection2")
                .user(user)
                .songCollectionId(2L)
                .build();
        //When
        songCollectionService.saveSongCollection(songCollection);
        SongCollection savedSongCollection = songCollectionService.saveSongCollection(songCollection2);
        try {
            songCollectionService.deleteById(savedSongCollection.getSongCollectionId());
        } catch (SongCollectionNotFoundException e) {
            fail();
        }
        //Then
        assertEquals(1,songCollectionRepository.count());
        songCollectionRepository.deleteAll();
    }

    @Test
    void findSongCollectionBySongCollectionName() {
        //Given
        User user = userRepository.save(User.builder().userName("Test user").build());
        SongCollection songCollection = SongCollection.builder()
                .songCollectionName("Test songCollection")
                .user(user)
                .songCollectionId(1L)
                .build();
        SongCollection songCollection2 = SongCollection.builder()
                .songCollectionName("Test songCollection2")
                .user(user)
                .songCollectionId(2L)
                .build();
        //When
        songCollectionService.saveSongCollection(songCollection);
        songCollectionService.saveSongCollection(songCollection2);
        List<SongCollection> foundSongCollections = songCollectionService.findSongCollectionBySongCollectionName("Test songCollection2");

        //Then
        assertEquals("Test songCollection2", foundSongCollections.get(0).getSongCollectionName());
        songCollectionRepository.deleteAll();
    }
}