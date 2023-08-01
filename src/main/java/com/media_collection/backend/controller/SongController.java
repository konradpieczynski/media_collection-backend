package com.media_collection.backend.controller;

import com.media_collection.backend.controller.exceptions.SongNotFoundException;
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

    @PostMapping(value ="/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createSong(@RequestBody SongDto songDto) {
        songService.saveSong(songMapper.mapToSong(songDto));
        return ResponseEntity.ok("New song " + songDto.getSongTitle() + " was successfully created");
    }

    @DeleteMapping(value = "/delete/{songId}")
    public ResponseEntity<Object> deleteSong(@PathVariable Long songId) throws SongNotFoundException {
        String songName = songService.findSongById(songId).getTitle();
        songService.deleteById(songId);
        return ResponseEntity.ok("Song " + songName + " was successfully deleted");
    }
}
