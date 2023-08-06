package com.media_collection.backend.controller;

import com.media_collection.backend.domain.AppleSongResponseDto;
import com.media_collection.backend.domain.ExternalMovieDatabaseDto;
import com.media_collection.backend.external_api.rapidapi.applemarketingtools.AppleMarketingToolsClient;
import com.media_collection.backend.external_api.rapidapi.moviesdatabase.MoviesDatabaseClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/external")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ExternalApiFeedTestController {

    private final AppleMarketingToolsClient appleMarketingToolsClient;

    private final MoviesDatabaseClient moviesDatabaseClient;
    @GetMapping(value = "applesong")
    public ResponseEntity<AppleSongResponseDto> getSongs() {
        return ResponseEntity.ok(appleMarketingToolsClient.getAppleSongFeedDto());
    }

    @GetMapping(value = "moviedatabase")
    public ResponseEntity<ExternalMovieDatabaseDto> getMovies() {
        return ResponseEntity.ok(moviesDatabaseClient.getExternalMovieDatabaseDto());
    }
}
