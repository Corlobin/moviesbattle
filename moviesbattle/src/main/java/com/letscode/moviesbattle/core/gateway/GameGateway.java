package com.letscode.moviesbattle.core.gateway;

import com.letscode.moviesbattle.core.domain.GameDomain;
import com.letscode.moviesbattle.core.domain.MovieDomain;

import java.util.List;

public interface GameGateway {

    GameDomain startGame(long userId);

    List<MovieDomain> getMovieGameList(long gameId);

    void sendAnswer(long gameId, long movieId);

    GameDomain getGame(long gameId);
}
