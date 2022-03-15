package com.letscode.moviesbattle.infra.client.omdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OmdbResponse (
    @JsonProperty("imdbID") String id,
    @JsonProperty("Title") String title,
    @JsonProperty("Released") String released,
    @JsonProperty("imdbRating") String imdbRating,
    @JsonProperty("imdbVotes") String imdbVotes,
    @JsonProperty("Year") String year
) {
}
