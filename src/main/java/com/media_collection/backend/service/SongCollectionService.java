package com.media_collection.backend.service;

import com.media_collection.backend.controller.exceptions.SongCollectionNotFoundException;
import com.media_collection.backend.domain.SongCollection;
import com.media_collection.backend.repository.SongCollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongCollectionService {
    private final SongCollectionRepository repository;

    public SongCollection saveSongCollection(SongCollection SongCollection) {
        return repository.save(SongCollection);
    }

    public SongCollection findSongCollectionById(Long SongCollectionId) throws SongCollectionNotFoundException {
        return repository.findById(SongCollectionId).orElseThrow(SongCollectionNotFoundException::new);
    }

    public List<SongCollection> getSongCollections() {
        return repository.findAll();
    }

    public void deleteById(Long SongCollectionId) throws SongCollectionNotFoundException {
        SongCollection toBeDeletedSongCollection = repository.findById(SongCollectionId).orElseThrow(SongCollectionNotFoundException::new);
        repository.delete(toBeDeletedSongCollection);
    }

    public List<SongCollection> findSongCollectionBySongCollectionName(String name) {
        return repository.findSongCollectionsBySongCollectionName(name);
    }
}
