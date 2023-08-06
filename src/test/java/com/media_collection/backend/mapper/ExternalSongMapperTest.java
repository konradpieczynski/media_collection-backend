package com.media_collection.backend.mapper;

import com.media_collection.backend.domain.AppleSongFeedDto;
import com.media_collection.backend.domain.AppleSongFeedResultsDto;
import com.media_collection.backend.domain.AppleSongResponseDto;
import com.media_collection.backend.domain.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ExternalSongMapperTest {

    @Autowired
    ExternalSongMapper externalSongMapper;
    @Test
    void mapToSongList() {
        //GIVEN
        List<AppleSongFeedResultsDto> feed = new ArrayList<>();
        feed.add(new AppleSongFeedResultsDto("test artists","test song"));
        AppleSongFeedDto resultDtos = new AppleSongFeedDto(feed);
        AppleSongResponseDto appleSongResponseDto = new AppleSongResponseDto(resultDtos);
        //WHEN
        List<Song> results = externalSongMapper.mapToSongList(appleSongResponseDto);
        //THEN
        assertEquals(1, results.size());
        assertEquals("test artists", results.get(0).getSongAuthor());
        assertEquals("test song", results.get(0).getSongTitle());
    }
}