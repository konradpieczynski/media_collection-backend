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
@Entity(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "join_movie_collection",
            joinColumns = {
                    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "movie_collection_id", referencedColumnName = "movie_collection_id")}
    )
    @Builder.Default
    private List<MovieCollection> movieCollectionList = new ArrayList<>();
}
