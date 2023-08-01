package com.media_collection.backend.controller;

import com.media_collection.backend.controller.exceptions.SongCollectionNotFoundException;
import com.media_collection.backend.domain.SongCollection;
import com.media_collection.backend.domain.SongCollectionDto;
import com.media_collection.backend.mapper.SongCollectionMapper;
import com.media_collection.backend.service.SongCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/songCollections")
@CrossOrigin("*")
@RequiredArgsConstructor
public class SongCollectionController {
    private final SongCollectionMapper songCollectionMapper;
    private final SongCollectionService songCollectionService;

    @GetMapping(value = "{songCollectionId}")
    public ResponseEntity<SongCollectionDto> getSongCollection(@PathVariable Long songCollectionId) throws SongCollectionNotFoundException {
        return ResponseEntity.ok(songCollectionMapper.mapToSongCollectionDto(songCollectionService.findSongCollectionById(songCollectionId)));
    }

    @GetMapping
    public ResponseEntity<List<SongCollectionDto>> getSongCollections() {
        return ResponseEntity.ok(songCollectionMapper.mapToSongCollectionDtoList(songCollectionService.getSongCollections()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SongCollectionDto> createSongCollection(@RequestBody SongCollectionDto songCollectionDto) {
        songCollectionService.saveSongCollection(songCollectionMapper.mapToSongCollection(songCollectionDto));
        return ResponseEntity.ok(songCollectionDto);
    }

    @PutMapping
    public ResponseEntity<SongCollectionDto> updateSongCollection(@RequestBody SongCollectionDto songCollectionDto) {
        SongCollection songCollection = songCollectionMapper.mapToSongCollection(songCollectionDto);
        SongCollection savedSongCollection = songCollectionService.saveSongCollection(songCollection);
        return ResponseEntity.ok(songCollectionMapper.mapToSongCollectionDto(savedSongCollection));
    }

    @DeleteMapping(value = "{songCollectionId}")
    public ResponseEntity<Void> deleteSongCollection(@PathVariable Long songCollectionId) throws SongCollectionNotFoundException {
        songCollectionService.deleteById(songCollectionId);
        return ResponseEntity.ok().build();
    }
}
