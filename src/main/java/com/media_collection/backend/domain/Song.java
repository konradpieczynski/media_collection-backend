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
@Entity(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "song_id")
    private Long songId;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "join_song_collection",
            joinColumns = {
                    @JoinColumn(name = "song_id", referencedColumnName = "song_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "song_collection_id", referencedColumnName = "song_collection_id")}
    )
    @Builder.Default
    private List<SongCollection> songCollectionList = new ArrayList<>();
}
