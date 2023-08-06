package com.media_collection.backend.repository;

import com.media_collection.backend.domain.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends CrudRepository<Song, Long> {
    @Override
    List<Song> findAll();
    List<Song> findSongsBySongTitle(String title);
}