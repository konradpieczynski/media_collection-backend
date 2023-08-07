package com.media_collection.backend.controller;

import com.media_collection.backend.domain.*;
import com.media_collection.backend.external_api.rapidapi.applemarketingtools.AppleMarketingToolsClient;
import com.media_collection.backend.external_api.rapidapi.moviesdatabase.MoviesDatabaseClient;
import com.media_collection.backend.mapper.ExternalMovieMapper;
import com.media_collection.backend.mapper.ExternalSongMapper;
import com.media_collection.backend.mapper.MovieMapper;
import com.media_collection.backend.mapper.SongMapper;
import com.media_collection.backend.service.MovieCollectionService;
import com.media_collection.backend.service.MovieService;
import com.media_collection.backend.service.SongCollectionService;
import com.media_collection.backend.service.SongService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(ExternalApiFeedTestController.class)
class ExternalApiFeedTestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private ExternalSongMapper externalSongMapper;

    @SpyBean
    private SongMapper songMapper;


    @SpyBean
    private ExternalMovieMapper externalMovieMapper;

    @SpyBean
    private MovieMapper movieMapper;

    @MockBean
    private AppleMarketingToolsClient appleMarketingToolsClient;

    @MockBean
    private MoviesDatabaseClient moviesDatabaseClient;

    @MockBean
    private SongService songService;

    @MockBean
    private SongCollectionService songCollectionService;

    @MockBean
    private MovieService movieService;

    @MockBean
    private MovieCollectionService movieCollectionService;

    @Test
    void shouldFetchSongDto() throws Exception {
        // Given
        Set<AppleSongFeedResultsDto> appleSongFeedResultsDtos = new HashSet<>();
        appleSongFeedResultsDtos.add(new AppleSongFeedResultsDto("test artist", "test name"));
        AppleSongFeedDto appleSongFeedDto = new AppleSongFeedDto(appleSongFeedResultsDtos);
        AppleSongResponseDto appleSongResponseDto = new AppleSongResponseDto(appleSongFeedDto);
        when(appleMarketingToolsClient.getAppleSongFeedDto()).thenReturn(appleSongResponseDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/external/applesong")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$.feed.results", Matchers.hasSize(1)));
    }
    @Test
    void shouldFetchMovieDto() throws Exception {
        // Given
        Set<ExternalMovieDatabaseResultsDto> resultsDtoList = new HashSet<>();
        resultsDtoList.add(
                new ExternalMovieDatabaseResultsDto(
                        new ExternalMovieDatabaseResultsOriginalTitleTextDto("test title"),
                        new ExternalMovieDatabaseResultsReleaseYear(1999)));
        ExternalMovieDatabaseDto results = new ExternalMovieDatabaseDto("1",resultsDtoList);
        when(moviesDatabaseClient.getExternalMovieDatabaseDto()).thenReturn(results);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/external/moviedatabase")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$.results", Matchers.hasSize(1)));
    }
}