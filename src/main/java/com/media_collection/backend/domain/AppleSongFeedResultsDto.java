package com.media_collection.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class AppleSongFeedResultsDto {
    @JsonProperty("artistName")
    String artistName;
    @JsonProperty("name")
    String name;
}
