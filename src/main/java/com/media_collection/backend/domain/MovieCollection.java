package com.media_collection.backend.domain;

import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @ManyToMany(mappedBy = "movieCollectionSet", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Movie> movieList = new HashSet<>();
    @PreRemove
    private void removeThisFromRelations() {
        user.getMovieCollectionList().remove(this);
        for (Movie movie : movieList) {
            movie.getMovieCollectionSet().remove(this);
        }
    }
}
