package com.media_collection.backend.scheduler;

import com.media_collection.backend.domain.Song;
import com.media_collection.backend.external_api.rapidapi.applemarketingtools.AppleMarketingToolsClient;
import com.media_collection.backend.mapper.ExternalSongMapper;
import com.media_collection.backend.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExternalApiSongsScheduler {

    private final SongService songService;
    private final AppleMarketingToolsClient appleMarketingToolsClient;
    private final ExternalSongMapper externalSongMapper;
    @Scheduled(cron = "0 50 * * * *")
    public void saveExternalSongs(){
        log.info("\nSaving external songs to database\n");
        List<Song> externalSongsList = externalSongMapper.mapToSongList(appleMarketingToolsClient.getAppleSongFeedDto());
        List<Song> songList = songService.getSongs();
        for (Song song: externalSongsList) {
            if (!songList.contains(song)){
                songService.saveSong(song);
            }
        }
        log.info("\nDone saving external songs to database\n");
    }
}
