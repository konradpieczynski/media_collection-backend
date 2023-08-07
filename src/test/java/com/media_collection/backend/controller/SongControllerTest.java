package com.media_collection.backend.controller;

import com.google.gson.Gson;
import com.media_collection.backend.domain.Song;
import com.media_collection.backend.domain.SongDto;
import com.media_collection.backend.mapper.SongMapper;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(SongController.class)
class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService songService;

    @MockBean
    private SongCollectionService songCollectionService;

    @SpyBean
    private SongMapper songMapper;

    @Test
    void shouldFetchEmptySongList() throws Exception {
        // Given
        when(songService.getSongs()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/songs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchSongList() throws Exception {
        // Given
        List<Song> songs = List.of(new Song(1L, "Test song", "Test author",new HashSet<>()));
        when(songService.getSongs()).thenReturn(songs);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/songs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].songId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].songTitle", Matchers.is("Test song")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].songAuthor", Matchers.is("Test author")));
    }

    @Test
    void shouldFetchSong() throws Exception {
        // Given
        Song song = new Song(1L, "Test song", "Test author",new HashSet<>());
        when(songService.findSongById(1L)).thenReturn(song);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/songs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.songId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.songTitle", Matchers.is("Test song")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.songAuthor", Matchers.is("Test author")));
    }

    @Test
    void shouldDeleteSong() throws Exception {
        //Given
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/songs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateSong() throws Exception {
        // Given
        Song song = new Song(1L, "Test song", "Test author",new HashSet<>());
        SongDto songDto = new SongDto(1L, "Test song", "Test author", new ArrayList<>());
        when(songService.saveSong(any(Song.class))).thenReturn(song);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(songDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/songs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.songId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.songTitle", Matchers.is("Test song")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.songAuthor", Matchers.is("Test author")));
    }

    @Test
    void shouldCreateSong() throws Exception {
        // Given
        SongDto songDto = new SongDto(1L, "Test song", "Test author", new ArrayList<>());
        Gson gson = new Gson();
        String jsonContent = gson.toJson(songDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/songs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}