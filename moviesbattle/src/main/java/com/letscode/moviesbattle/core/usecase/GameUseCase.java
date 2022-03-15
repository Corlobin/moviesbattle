package com.letscode.moviesbattle.core.usecase;

import com.letscode.moviesbattle.core.domain.GameDomain;
import com.letscode.moviesbattle.core.domain.MovieDomain;
import com.letscode.moviesbattle.core.gateway.GameGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameUseCase {

    private final GameGateway gameGateway;

    public GameDomain startGame(long userId) {
        return gameGateway.startGame(userId);
    }

    public List<MovieDomain> getMovieGameList(long gameId) {
        return gameGateway.getMovieGameList(gameId);
    }

    public void sendAnswer(long gameId, long movieId) {
        gameGateway.sendAnswer(gameId, movieId);
    }

    public GameDomain getGameStatus(long gameId) {
        return gameGateway.getGame(gameId);
    }
}
