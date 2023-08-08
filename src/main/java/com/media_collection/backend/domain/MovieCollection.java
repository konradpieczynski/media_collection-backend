package com.media_collection.backend.domain;

import lombok.*;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "movies_collections")
public class MovieCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_collection_id")
    private Long movieCollectionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "movie_collection_name")
    private String movieCollectionName;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "collection_movies",
            joinColumns = @JoinColumn(name = "movie_collection_id", referencedColumnName = "movie_collection_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
    )
    @Builder.Default
    private Set<Movie> movies = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieCollection that = (MovieCollection) o;
        return Objects.equals(movieCollectionId, that.movieCollectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieCollectionId);
    }

    @PreRemove
    private void removeThisFromRelations() {
        for (Movie movie : movies) {
            movie.getMovieCollections().remove(this);
        }
    }
}
