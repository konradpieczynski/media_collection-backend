package com.media_collection.backend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "collection_name")
    private String name;

    @ManyToMany(mappedBy = "movieCollectionList", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Movie> movieList = new ArrayList<>();
    @PreRemove
    private void removeThisFromRelations() {
        user.getMovieCollectionList().remove(this);
        for (Movie movie : movieList) {
            movie.getMovieCollectionList().remove(this);
        }
    }
}
