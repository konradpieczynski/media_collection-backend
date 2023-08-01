package com.media_collection.backend.service;

import com.media_collection.backend.controller.exceptions.SongNotFoundException;
import com.media_collection.backend.domain.Song;
import com.media_collection.backend.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository repository;

    public Song saveSong(Song Song) {
        return repository.save(Song);
    }

    public Song findSongById(Long SongId) throws SongNotFoundException {
        return repository.findById(SongId).orElseThrow(SongNotFoundException::new);
    }

    public List<Song> getSongs() {
        return repository.findAll();
    }

    public void deleteById(Long SongId) throws SongNotFoundException {
        Song toBeDeletedSong = repository.findById(SongId).orElseThrow(SongNotFoundException::new);
        repository.delete(toBeDeletedSong);
    }

    public List<Song> findSongBySongName(String title) {
        return repository.findSongsByTitle(title);
    }
}
