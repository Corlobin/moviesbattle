package com.letscode.moviesbattle.infra.client.omdb;

import feign.Headers;
import feign.QueryMap;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

@FeignClient("OmdbApiClient")
@Headers({
    "Content-Type: application/json; charset=utf-8",
    "Accept: application/json; charset=utf-8",
})
public interface OmdbApiClient {
    @RequestLine("GET /")
    OmdbResponse getOmdbData(@QueryMap Map<String, String> params);

    default OmdbResponse getByImdbId(String apiKey, String id) {
        var args = Map.of("apikey", apiKey, "i", id);
        return getOmdbData(args);
    }
}
