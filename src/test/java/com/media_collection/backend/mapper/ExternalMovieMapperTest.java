package com.media_collection.backend.mapper;

import com.media_collection.backend.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ExternalMovieMapperTest {
    @Autowired
    ExternalMovieMapper externalMovieMapper;
    @Test
    void mapToMovieList() {
        //GIVEN
        Set<ExternalMovieDatabaseResultsDto> resultsDtos = new HashSet<>();
        resultsDtos.add(new ExternalMovieDatabaseResultsDto(
                new ExternalMovieDatabaseResultsOriginalTitleTextDto("Test title"),
                new ExternalMovieDatabaseResultsReleaseYear(1999)));
        ExternalMovieDatabaseDto databaseDto = new ExternalMovieDatabaseDto("1", resultsDtos);
        //WHEN
        List<Movie> results = externalMovieMapper.mapToMovieList(databaseDto);
        //THEN
        assertEquals(1,results.size());
        assertEquals("Test title", results.get(0).getMovieTitle());
        assertEquals(1999, results.get(0).getMovieYear());
    }
}