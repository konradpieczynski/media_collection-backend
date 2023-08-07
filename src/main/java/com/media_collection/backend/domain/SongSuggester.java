package com.media_collection.backend.domain;

import com.media_collection.backend.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SongSuggester {

    @Autowired
    private final SongService songService;
    Set<Song> makeSuggestions(){
        Set<Song> suggestions = new HashSet<>();
        List<Song> songs = songService.getSongs();
        if (!songs.isEmpty()) {
            Random random = new Random();
            for (int i = 0; i < 3; i++) {
                suggestions.add(songs.get(random.nextInt(songs.size())));
            }
        }
        return suggestions;
    }
}
