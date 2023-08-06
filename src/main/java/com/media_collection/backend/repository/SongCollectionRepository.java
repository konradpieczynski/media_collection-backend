package com.media_collection.backend.repository;

import com.media_collection.backend.domain.SongCollection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongCollectionRepository extends CrudRepository<SongCollection, Long> {
    @Override
    List<SongCollection> findAll();

    List<SongCollection> findSongCollectionsBySongCollectionName(String name);
}