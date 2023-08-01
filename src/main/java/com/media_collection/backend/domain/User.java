package com.media_collection.backend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @Builder.Default
    public List<SongCollection> songCollectionList = new ArrayList<>();

    @OneToMany(targetEntity = MovieCollection.class,
            mappedBy = "user",
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @Builder.Default
    public List<MovieCollection> movieCollectionList = new ArrayList<>();
}
