package com.media_collection.backend.controller;

import com.google.gson.Gson;
import com.media_collection.backend.domain.*;
import com.media_collection.backend.mapper.UserMapper;
import com.media_collection.backend.service.*;
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
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SongCollectionService songCollectionService;

    @MockBean
    private MovieCollectionService movieCollectionService;

    @MockBean
    private SongService songService;

    @MockBean
    private MovieService movieService;

    @SpyBean
    private UserMapper userMapper;

    @SpyBean
    private SuggestionsFactory suggestionsFactory;

    @SpyBean
    private SongSuggester songSuggester;
    @SpyBean
    private MovieSuggester movieSuggester;

    @Test
    void shouldFetchEmptyUserList() throws Exception {
        // Given
        when(userService.getUsers()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchUserList() throws Exception {
        // Given
        List<User> users = List.of(new User(1L,
                "Test user",
                Suggestions.SONGS,
                new HashSet<>(),
                new HashSet<>()));
        when(userService.getUsers()).thenReturn(users);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName", Matchers.is("Test user")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].suggestions.type", Matchers.is("songs")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].movieCollectionList", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].songCollectionList", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchUser() throws Exception {
        // Given
        User user = new User(1L,
                "Test user",
                Suggestions.SONGS,
                new HashSet<>(),
                new HashSet<>());
        when(userService.findUserById(1L)).thenReturn(user);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", Matchers.is("Test user")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.suggestions.type", Matchers.is("songs")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieCollectionList", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.songCollectionList", Matchers.hasSize(0)));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        //Given
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        // Given
        User user = new User(1L,
                "Test user",
                Suggestions.SONGS,
                new HashSet<>(),
                new HashSet<>());
        UserDto userDto = new UserDto(1L,
                "Test user",
                suggestionsFactory.makeSuggestions("songs"),
                new ArrayList<>(),
                new ArrayList<>());
        when(userService.saveUser(any(User.class))).thenReturn(user);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", Matchers.is("Test user")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.suggestions.type", Matchers.is("songs")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieCollectionList", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.songCollectionList", Matchers.hasSize(0)));
    }

    @Test
    void shouldCreateUser() throws Exception {
        // Given
        UserDto userDto = new UserDto(1L,
                "Test user",
                suggestionsFactory.makeSuggestions("songs"),
                new ArrayList<>(),
                new ArrayList<>());
        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}