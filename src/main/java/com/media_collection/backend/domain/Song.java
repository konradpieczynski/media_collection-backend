package com.media_collection.backend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        if (!Objects.equals(songTitle, song.songTitle)) return false;
        return Objects.equals(songAuthor, song.songAuthor);
    }

    @Override
    public int hashCode() {
        int result = songTitle != null ? songTitle.hashCode() : 0;
        result = 31 * result + (songAuthor != null ? songAuthor.hashCode() : 0);
        return result;
    }
}
