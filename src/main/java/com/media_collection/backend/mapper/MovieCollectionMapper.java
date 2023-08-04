package com.media_collection.backend.mapper;

import com.media_collection.backend.controller.exceptions.MovieNotFoundException;
import com.media_collection.backend.domain.*;
import com.media_collection.backend.service.MovieService;
import com.media_collection.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieCollectionMapper {

    @Autowired
    MovieService movieService;
    @Autowired
    UserService userService;
    public MovieCollection mapToMovieCollection(final MovieCollectionDto movieCollectionDto) {
        return MovieCollection.builder()
                .movieCollectionId(movieCollectionDto.getMovieCollectionId())
                .movieCollectionName(movieCollectionDto.getMovieCollectionName())
                .user(userService.findUserByUserName(movieCollectionDto.getUser()).get(0))
                .movieList(movieCollectionDto.getMovieCollectionList()
                        .stream()
                        .map(movieId -> {
                            try {
                                return movieService.findMovieById(movieId);
                            } catch (MovieNotFoundException e) {
                                return null;
                            }
                        })
                        .collect(Collectors.toList()))
                .build();
    }

    public MovieCollectionDto mapToMovieCollectionDto(final MovieCollection movieCollection) {
        return MovieCollectionDto.builder()
                .movieCollectionId(movieCollection.getMovieCollectionId())
                .user(movieCollection.getUser().getUserName())
                .movieCollectionName(movieCollection.getMovieCollectionName())
                .movieCollectionList(movieCollection.getMovieList().stream().map(Movie::getMovieId).collect(Collectors.toList()))
                .build();
    }

    public List<MovieCollectionDto> mapToMovieCollectionDtoList(final List<MovieCollection> movieCollectionList) {
        return movieCollectionList.stream()
                .map(this::mapToMovieCollectionDto)
                .toList();
    }
}
