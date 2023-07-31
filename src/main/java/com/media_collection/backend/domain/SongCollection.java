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
    private String name;

    @ManyToMany(mappedBy = "songCollectionList", cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @Builder.Default
    private List<Song> songList = new ArrayList<>();

    @PreRemove
    private void removeThisFromRelations() {
        user.getFavoriteSongList().remove(this);
        for (Song song : songList) {
            song.getSongCollectionList().remove(this);
        }
    }
}
