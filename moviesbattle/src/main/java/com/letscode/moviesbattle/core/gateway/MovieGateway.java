package com.letscode.moviesbattle.core.gateway;

import com.letscode.moviesbattle.core.domain.MovieDomain;
import com.letscode.moviesbattle.entrypoint.dto.MovieDTO;

import java.util.List;

public interface MovieGateway {

    void initialize();

    List<MovieDomain> getAll();
}
