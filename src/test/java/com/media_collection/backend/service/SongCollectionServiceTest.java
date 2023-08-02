package com.media_collection.backend.service;

import com.media_collection.backend.controller.exceptions.SongCollectionNotFoundException;
import com.media_collection.backend.domain.SongCollection;
import com.media_collection.backend.repository.SongCollectionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
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
    
    @Test
    void saveSongCollection() {
        //Given
        SongCollection songCollection = SongCollection.builder()
                .name("Test songCollection")
                .build();
        //When
        SongCollection savedSongCollection = songCollectionService.saveSongCollection(songCollection);
        //Then
        assertEquals(songCollection.getName(),savedSongCollection.getName());
        assertEquals(1,songCollectionService.findSongCollectionBySongCollectionName("Test songCollection").size());
        songCollectionRepository.deleteAll();
    }

    @Test
    void findSongCollectionById() {
        //Given
        SongCollection songCollection = SongCollection.builder()
                .songCollectionId(1L)
                .name("Test songCollection")
                .build();
        //When
        SongCollection savedSongCollection = songCollectionService.saveSongCollection(songCollection);
        //Then
        assertEquals(songCollection.getSongCollectionId(),savedSongCollection.getSongCollectionId());
        try {
            assertEquals(1L, songCollectionService.findSongCollectionById(1L).getSongCollectionId());
        } catch (SongCollectionNotFoundException e) {
            fail();
        }
        songCollectionRepository.deleteAll();
    }

    @Test
    void getSongCollections() {
        //Given
        SongCollection songCollection = SongCollection.builder()
                .name("Test songCollection")
                .build();
        SongCollection songCollection2 = SongCollection.builder()
                .name("Test songCollection2")
                .build();
        //When
        songCollectionService.saveSongCollection(songCollection);
        songCollectionService.saveSongCollection(songCollection2);
        //Then
        assertEquals(2,songCollectionRepository.count());
        songCollectionRepository.deleteAll();
    }

    @Test
    void deleteById() {
        //Given
        SongCollection songCollection = SongCollection.builder()
                .name("Test songCollection")
                .songCollectionId(1L)
                .build();
        SongCollection songCollection2 = SongCollection.builder()
                .name("Test songCollection2")
                .songCollectionId(2L)
                .build();
        //When
        songCollectionService.saveSongCollection(songCollection);
        songCollectionService.saveSongCollection(songCollection2);
        try {
            songCollectionService.deleteById(2L);
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
        SongCollection songCollection = SongCollection.builder()
                .name("Test songCollection")
                .songCollectionId(1L)
                .build();
        SongCollection songCollection2 = SongCollection.builder()
                .name("Test songCollection2")
                .songCollectionId(2L)
                .build();
        //When
        songCollectionService.saveSongCollection(songCollection);
        songCollectionService.saveSongCollection(songCollection2);
        List<SongCollection> foundSongCollections = songCollectionService.findSongCollectionBySongCollectionName("Test songCollection2");

        //Then
        assertEquals("Test songCollection2", foundSongCollections.get(0).getName());
        songCollectionRepository.deleteAll();
    }
}