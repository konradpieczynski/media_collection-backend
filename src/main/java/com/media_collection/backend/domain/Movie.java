package com.media_collection.backend.domain;

import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "movie_title")
    private String movieTitle;

    @Column(name = "movie_author")
    private int movieYear;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "join_movie_collection",
            joinColumns = {
                    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "movie_collection_id", referencedColumnName = "movie_collection_id")}
    )
    @Builder.Default
    private List<MovieCollection> movieCollectionList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (movieYear != movie.movieYear) return false;
        return movieTitle.equals(movie.movieTitle);
    }

    @Override
    public int hashCode() {
        int result = movieTitle.hashCode();
        result = 31 * result + movieYear;
        return result;
    }
}
