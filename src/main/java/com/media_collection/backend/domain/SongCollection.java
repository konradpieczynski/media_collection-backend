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
@Entity(name = "songs_collections")
public class SongCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "song_collection_id")
    private Long songCollectionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "collection_name")
    private String songCollectionName;

    @ManyToMany(mappedBy = "songCollectionSet", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Song> songList = new HashSet<>();

    @PreRemove
    private void removeThisFromRelations() {
        user.getSongCollectionList().remove(this);
        for (Song song : songList) {
            song.getSongCollectionSet().remove(this);
        }
    }
}
