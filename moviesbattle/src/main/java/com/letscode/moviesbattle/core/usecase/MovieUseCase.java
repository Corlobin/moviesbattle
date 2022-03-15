package com.letscode.moviesbattle.core.usecase;

import com.letscode.moviesbattle.core.domain.MovieDomain;
import com.letscode.moviesbattle.core.gateway.MovieGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieUseCase {
    private final MovieGateway movieGateway;

    public void initialize() {
        movieGateway.initialize();
    }

    public List<MovieDomain> getAll() {
        return movieGateway.getAll();
    }
}
