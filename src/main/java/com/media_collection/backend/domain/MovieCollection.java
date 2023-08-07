package com.media_collection.backend.domain;

import lombok.*;

import jakarta.persistence.*;

import java.util.HashSet;
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

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "join_movie_collection",
            joinColumns = {
                    @JoinColumn(name = "movie_collection_id", referencedColumnName = "movie_collection_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")}
    )
    @Builder.Default
    private Set<Movie> movies = new HashSet<>();
    @PreRemove
    private void removeThisFromRelations() {
        user.getMovieCollectionList().remove(this);
        for (Movie movie : movies) {
            movie.getMovieCollections().remove(this);
        }
    }
}
