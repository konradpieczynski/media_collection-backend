package com.media_collection.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

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


    @ManyToMany(mappedBy = "movies", cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private Set<MovieCollection> movieCollections = new HashSet<>();

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

    @PreRemove
    private void removeThisFromRelations() {
        for (MovieCollection movieCollection : movieCollections) {
            movieCollection.getMovies().remove(this);
        }
    }
}
