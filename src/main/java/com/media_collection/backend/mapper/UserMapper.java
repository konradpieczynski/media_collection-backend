package com.media_collection.backend.mapper;

import com.media_collection.backend.controller.exceptions.MovieCollectionNotFoundException;
import com.media_collection.backend.controller.exceptions.SongCollectionNotFoundException;
import com.media_collection.backend.domain.*;
import com.media_collection.backend.service.MovieCollectionService;
import com.media_collection.backend.service.SongCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    @Autowired
    SuggestionsFactory suggestionsFactory;
    @Autowired
    SongCollectionService songCollectionService;
    @Autowired
    MovieCollectionService movieCollectionService;
    public User mapToUser(final UserDto userDto){
        return User.builder()
                .userId(userDto.getUserId())
                .userName(userDto.getUserName())
                .movieCollections(userDto.getMovieCollectionList()
                        .stream()
                        .map(id -> {
                            try {
                                return movieCollectionService.findMovieCollectionById(id);
                            } catch (MovieCollectionNotFoundException e) {
                                return null;
                            }
                        })
                        .collect(Collectors.toSet())
                )
                .songCollections(userDto.getSongCollectionList()
                        .stream()
                        .map(id -> {
                            try {
                                return songCollectionService.findSongCollectionById(id);
                            } catch (SongCollectionNotFoundException e) {
                                return null;
                            }
                        })
                        .collect(Collectors.toSet())
                )
                .suggestions(Suggestions.valueOf(userDto.getSuggestions().getType().toUpperCase()))
                .build();
    }

    public UserDto mapToUserDto(final User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .suggestions(suggestionsFactory.makeSuggestions(user.getSuggestions().getValue()))
                .movieCollectionList(user.getMovieCollections()
                                .stream()
                                .map(MovieCollection::getMovieCollectionId)
                                .collect(Collectors.toSet()))
                .songCollectionList(user.getSongCollections()
                        .stream()
                        .map(SongCollection::getSongCollectionId)
                        .collect(Collectors.toSet()))
                .build();
    }
    public List<UserDto> mapToUserDtoList(final List<User> userList) {
        return userList.stream()
                .map(this::mapToUserDto)
                .toList();
    }
}
