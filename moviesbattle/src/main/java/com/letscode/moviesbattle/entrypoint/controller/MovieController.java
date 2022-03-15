package com.letscode.moviesbattle.entrypoint.controller;

import com.letscode.moviesbattle.core.usecase.MovieUseCase;
import com.letscode.moviesbattle.entrypoint.dto.MovieDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieUseCase movieUseCase;

    @PostMapping("/initialize")
    public void initialize() {
        movieUseCase.initialize();
    }

    @GetMapping
    public List<MovieDTO> getAll() {
        return movieUseCase.getAll()
            .stream()
            .map(movie -> new MovieDTO(movie.title()))
            .collect(Collectors.toList());
    }
}
