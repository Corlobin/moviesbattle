package com.letscode.moviesbattle.infra.gateway;

import com.letscode.moviesbattle.core.domain.MovieDomain;
import com.letscode.moviesbattle.core.gateway.MovieGateway;
import com.letscode.moviesbattle.infra.client.ScrapingClient;
import com.letscode.moviesbattle.infra.client.omdb.OmdbApiClient;
import com.letscode.moviesbattle.infra.client.omdb.OmdbResponse;
import com.letscode.moviesbattle.infra.database.entity.Movie;
import com.letscode.moviesbattle.infra.database.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieGatewayImpl implements MovieGateway {

    @Value("${spring.imdb.api-key}")
    private String imdbApiKey;

    private static final NumberFormat nf = new DecimalFormat("#,###.00");
    private static final NumberFormat nf2 = new DecimalFormat("#,###");

    private final ScrapingClient scrapingClient;
    private final OmdbApiClient omdbApiClient;
    private final MovieRepository movieRepository;

    @Override
    public void initialize() {
        scrapingClient.list()
            .parallelStream()
            .map(movie -> omdbApiClient.getByImdbId(imdbApiKey, movie.getRight()))
            .map(this::getMovieRepositoryData)
            .filter(Objects::nonNull)
            .forEach(movieRepository::save);
    }

    @Override
    public List<MovieDomain> getAll() {
        return movieRepository.findAll()
            .stream()
            .map(movie -> new MovieDomain(movie.getId(), movie.getTitle()))
            .collect(Collectors.toList());
    }

    // Could use MapStruct :)
    private Movie getMovieRepositoryData(OmdbResponse omdb) {
        try {
            if (omdb.imdbRating().equals("N/A") || omdb.imdbVotes().equals("N/A")) {
                return null;
            }
            return Movie.builder()
                .imdbId(omdb.id())
                .imdbVotes(nf.parse(omdb.imdbVotes()).toString())
                .imdbRating(omdb.imdbRating().replaceAll(",", "."))
                .title(omdb.title())
                .year(omdb.year())
                .build();
        } catch (Exception e) {
            log.error("Ops... campo mal formatado {}", omdb);
            return null;
        }
    }
}
