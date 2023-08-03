package com.media_collection.backend.repository;

import com.media_collection.backend.domain.SongCollection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongCollectionRepository extends CrudRepository<SongCollection, Long> {

    @Override
    SongCollection save(SongCollection SongCollection);

    @Override
    Optional<SongCollection> findById(Long SongCollectionId);

    @Override
    List<SongCollection> findAll();

    @Override
    void deleteById(Long SongCollectionId);

    List<SongCollection> findSongCollectionsBySongCollectionName(String name);
}