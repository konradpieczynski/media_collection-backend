package com.media_collection.backend.controller;

import com.media_collection.backend.controller.exceptions.SongNotFoundException;
import com.media_collection.backend.domain.Song;
import com.media_collection.backend.domain.SongDto;
import com.media_collection.backend.mapper.SongMapper;
import com.media_collection.backend.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/songs")
@CrossOrigin("*")
@RequiredArgsConstructor
public class SongController {
    private final SongMapper songMapper;
    private final SongService songService;

    @GetMapping(value = "{songId}")
    public ResponseEntity<SongDto> getSong(@PathVariable Long songId) throws SongNotFoundException {
        return ResponseEntity.ok(songMapper.mapToSongDto(songService.findSongById(songId)));
    }

    @GetMapping
    public ResponseEntity<List<SongDto>> getSongs() {
        return ResponseEntity.ok(songMapper.mapToSongDtoList(songService.getSongs()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSong(@RequestBody SongDto songDto) {
        songService.saveSong(songMapper.mapToSong(songDto));
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<SongDto> updateSong(@RequestBody SongDto songDto) {
        Song song = songMapper.mapToSong(songDto);
        Song savedSong = songService.saveSong(song);
        return ResponseEntity.ok(songMapper.mapToSongDto(savedSong));
    }

    @DeleteMapping(value = "{songId}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long songId) throws SongNotFoundException {
        songService.deleteById(songId);
        return ResponseEntity.ok().build();
    }
}
