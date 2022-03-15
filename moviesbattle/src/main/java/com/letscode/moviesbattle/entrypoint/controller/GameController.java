package com.letscode.moviesbattle.entrypoint.controller;


import com.letscode.moviesbattle.core.usecase.GameUseCase;
import com.letscode.moviesbattle.entrypoint.dto.GameDTO;
import com.letscode.moviesbattle.entrypoint.dto.GameMovieDTO;
import com.letscode.moviesbattle.entrypoint.dto.GameStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final GameUseCase gameUseCase;

    @PostMapping
    public GameDTO startGame(@RequestHeader("user_id") long userId) {
        return new GameDTO(gameUseCase.startGame(userId).gameId());
    }

    @GetMapping("/{game_id}")
    public List<GameMovieDTO> getMovieGameList(@PathVariable("game_id") long gameId) {
        return gameUseCase.getMovieGameList(gameId)
            .stream()
            .map(movie -> new GameMovieDTO(movie.id(), movie.title()))
            .collect(Collectors.toList());
    }

    @GetMapping("/{game_id}/status")
    public GameStatusDTO getGameStatus(@PathVariable("game_id") long gameId) {
        return Optional.of(gameUseCase.getGameStatus(gameId))
            .map(game -> new GameStatusDTO(game.round(), game.correctAnswers(), game.wrongAnswers()))
            .orElseThrow();
    }

    @PutMapping("/{game_id}/{movie_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendGameAnswer(@PathVariable("game_id") long gameId,
                               @PathVariable("movie_id") long movieId) {
        gameUseCase.sendAnswer(gameId, movieId);
    }

}
