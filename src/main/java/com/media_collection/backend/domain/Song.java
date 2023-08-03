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

    @Column(name = "song_title")
    private String songTitle;

    @Column(name = "song_author")
    private String songAuthor;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
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
