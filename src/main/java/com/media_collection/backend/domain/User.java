package com.media_collection.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    @NotNull
    private String userName;

    @Column(name = "suggestions")
    @Enumerated(EnumType.STRING)
    private Suggestions suggestions;

    @OneToMany(targetEntity = SongCollection.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    public Set<SongCollection> songCollectionList = new HashSet<>();

    @OneToMany(targetEntity = MovieCollection.class,
            mappedBy = "user",
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @Builder.Default
    public Set<MovieCollection> movieCollectionList = new HashSet<>();
}
