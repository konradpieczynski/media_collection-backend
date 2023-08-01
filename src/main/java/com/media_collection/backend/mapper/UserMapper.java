package com.media_collection.backend.mapper;

import com.media_collection.backend.controller.exceptions.MovieCollectionNotFoundException;
import com.media_collection.backend.controller.exceptions.SongCollectionNotFoundException;
import com.media_collection.backend.domain.*;
import com.media_collection.backend.service.MovieCollectionService;
import com.media_collection.backend.service.SongCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .movieCollectionList(userDto.getMovieCollectionList()
                        .stream()
                        .map(id -> {
                            try {
                                return movieCollectionService.findMovieCollectionById(id);
                            } catch (MovieCollectionNotFoundException e) {
                                return null;
                            }
                        })
                        .toList()
                )
                .songCollectionList(userDto.getSongCollectionList()
                        .stream()
                        .map(id -> {
                            try {
                                return songCollectionService.findSongCollectionById(id);
                            } catch (SongCollectionNotFoundException e) {
                                return null;
                            }
                        })
                        .toList()
                )
                .suggestions(Suggestions.valueOf(userDto.getSuggestions().getType()))
                .build();
    }

    public UserDto mapToUserDto(final User user) {
        return UserDto.builder()
                .userName(user.getUserName())
                .suggestions(suggestionsFactory.makeSuggestions(user.getSuggestions().getValue()))
                .movieCollectionList(user.getMovieCollectionList()
                                .stream()
                                .map(MovieCollection::getMovieCollectionId)
                                .toList())
                .songCollectionList(user.getSongCollectionList()
                        .stream()
                        .map(SongCollection::getSongCollectionId)
                        .toList())
                .build();
    }
    public List<UserDto> mapToUserDtoList(final List<User> userList) {
        return userList.stream()
                .map(this::mapToUserDto)
                .toList();
    }
}
