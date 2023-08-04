package com.media_collection.backend.controller;

import com.google.gson.Gson;
import com.media_collection.backend.domain.SongCollection;
import com.media_collection.backend.domain.SongCollectionDto;
import com.media_collection.backend.domain.Suggestions;
import com.media_collection.backend.domain.User;
import com.media_collection.backend.mapper.SongCollectionMapper;
import com.media_collection.backend.service.SongCollectionService;
import com.media_collection.backend.service.SongService;
import com.media_collection.backend.service.UserService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(SongCollectionController.class)
class SongCollectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongCollectionService songCollectionService;

    @MockBean
    private SongService songService;

    @MockBean
    private UserService userService;

    @SpyBean
    private SongCollectionMapper songCollectionMapper;

    @Test
    void shouldFetchEmptySongCollectionList() throws Exception {
        // Given
        when(songCollectionService.getSongCollections()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/songCollections")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchSongCollectionList() throws Exception {
        // Given
        User user = new User(1L,
                "Test User",
                Suggestions.SONGS,
                new ArrayList<>(),
                new ArrayList<>());
        SongCollection songCollection = new SongCollection(1L, user, "Test songCollection",new ArrayList<>());
        List<SongCollection> songCollections = List.of(songCollection);
        when(songCollectionService.getSongCollections()).thenReturn(songCollections);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/songCollections")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].songCollectionId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].songCollectionName", Matchers.is("Test songCollection")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].songCollectionList", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchSongCollection() throws Exception {
        // Given
        User user = new User(1L,
                "Test User",
                Suggestions.SONGS,
                new ArrayList<>(),
                new ArrayList<>());
        SongCollection songCollection = new SongCollection(1L, user, "Test songCollection",new ArrayList<>());
        when(songCollectionService.findSongCollectionById(1L)).thenReturn(songCollection);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/songCollections/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.songCollectionId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.songCollectionName", Matchers.is("Test songCollection")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.songCollectionList", Matchers.hasSize(0)));
    }

    @Test
    void shouldDeleteSongCollection() throws Exception {
        //Given
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/songCollections/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateSongCollection() throws Exception {
        // Given
        User user = new User(1L,
                "Test User",
                Suggestions.SONGS,
                new ArrayList<>(),
                new ArrayList<>());
        SongCollection songCollection = new SongCollection(1L, user, "Test songCollection",new ArrayList<>());
        SongCollectionDto songCollectionDto = new SongCollectionDto(1L, "Test user", "Test songCollection", new ArrayList<>());
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.findUserByUserName(songCollectionDto.getUser())).thenReturn(userList);
        when(songCollectionService.saveSongCollection(any(SongCollection.class))).thenReturn(songCollection);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(songCollectionDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/songCollections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.songCollectionId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.songCollectionName", Matchers.is("Test songCollection")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.songCollectionList", Matchers.hasSize(0)));
    }

    @Test
    void shouldCreateSongCollection() throws Exception {
        // Given
        User user = new User(1L,
                "Test User",
                Suggestions.SONGS,
                new ArrayList<>(),
                new ArrayList<>());
        SongCollectionDto songCollectionDto = new SongCollectionDto(1L, "Test user", "Test songCollection", new ArrayList<>());
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.findUserByUserName(songCollectionDto.getUser())).thenReturn(userList);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(songCollectionDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/songCollections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}