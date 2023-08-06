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
public class ExternalMovieDatabaseResultsDto {
    @JsonProperty("originalTitleText")
    ExternalMovieDatabaseResultsOriginalTitleTextDto originalTitleTextDto;
    @JsonProperty("releaseYear")
    ExternalMovieDatabaseResultsReleaseYear releaseYear;
}
