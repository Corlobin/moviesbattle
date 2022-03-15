package com.letscode.moviesbattle.infra.gateway;

import com.letscode.moviesbattle.core.domain.GameDomain;
import com.letscode.moviesbattle.core.domain.MovieDomain;
import com.letscode.moviesbattle.core.gateway.GameGateway;
import com.letscode.moviesbattle.entrypoint.exception.GameNotFound;
import com.letscode.moviesbattle.entrypoint.exception.UserLostGame;
import com.letscode.moviesbattle.infra.database.entity.Game;
import com.letscode.moviesbattle.infra.database.entity.Movie;
import com.letscode.moviesbattle.infra.database.repository.GameRepository;
import com.letscode.moviesbattle.infra.database.repository.MovieRepository;
import com.letscode.moviesbattle.infra.database.repository.UserRepository;
import com.letscode.moviesbattle.infra.exception.UserNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameGatewayImpl implements GameGateway {

    private final GameRepository gameRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    // Java Records ... trade-offs ;)
    @Override
    public GameDomain getGame(long gameId) {
        return gameRepository.findById(gameId)
            .map(game -> new GameDomain(game.getId(), game.getRound(),
                game.getCorrectAnswers(), game.getWrongAnswers(),
                game.getMaxRanking(), game.getUserId(), game.getMovieOne(), game.getMovieTwo()))
            .orElseThrow();
    }

    @Override
    public void sendAnswer(long gameId, long movieId) {
        Game game = gameRepository.findById(gameId)
            .orElseThrow(GameNotFound::new);

        Movie highScoreMovie = LongStream.of(game.getMovieOne(), game.getMovieTwo())
            .mapToObj(movieRepository::findById)
            .map(Optional::get)
            .max(Comparator.comparing(Movie::getPontuation))
            .orElseThrow();

        if (movieId == highScoreMovie.getId()) {
            log.info("User acertou o filme de maior pontuação!! parabéns {}.", game.getUserId());
            game.incrementCorrectAnswers();
        } else {
            log.info("User error o filme de maior pontuação!! tente novamente.. {}.", game.getUserId());
            game.decrementCorrectAnswers();
        }

        generateNewMovies(game);
        gameRepository.save(game);
        validateMaximumAttempts(game);
    }

    private void generateNewMovies(Game game) {
        List<Movie> random = movieRepository.findRandom();
        game.setMovieOne(random.get(0).getId());
        game.setMovieTwo(random.get(1).getId());
    }

    private void validateMaximumAttempts(Game game) {
        if (game.getWrongAnswers() >= 3) {
            log.info("User perdeu o jogo :(");
            game.setCorrectAnswers(0);
            game.setWrongAnswers(0);
            game.updateMaxRanking();
            game.setRound(1);
            gameRepository.save(game);
            throw new UserLostGame();
        }
    }

    @Override
    public GameDomain startGame(long userId) {
        validateUser(userId);
        Game returnedGame = gameRepository.findByUserId(userId)
            .map(game -> updateActualGame(userId, game))
            .orElseGet(() -> createNewGame(userId));
        return new GameDomain(returnedGame.getId(), returnedGame.getRound(),
            returnedGame.getCorrectAnswers(), returnedGame.getWrongAnswers(),
            returnedGame.getMaxRanking(), userId, returnedGame.getMovieOne(),
            returnedGame.getMovieTwo());
    }

    private void validateUser(long userId) {
        userRepository.findById(userId)
            .orElseThrow(UserNotFound::new);
    }

    @Override
    public List<MovieDomain> getMovieGameList(long gameId) {
        Game game = gameRepository.findById(gameId)
            .orElseThrow();
        return getMovie(game.getMovieOne(), game.getMovieTwo());
    }

    private List<MovieDomain> getMovie(long movieOne, long movieTwo) {
        return Stream.of( movieRepository.findById(movieOne).orElseThrow(), movieRepository.findById(movieTwo).orElseThrow())
            .map(movie -> new MovieDomain(movie.getId(), movie.getTitle()))
            .collect(Collectors.toList());
    }

    private Game createNewGame(long userId) {
        List<Movie> random = movieRepository.findRandom();
        Game game = new Game();
        resetGame(userId, game, random);
        return gameRepository.save(game);
    }

    private Game updateActualGame(long userId, Game game) {
        List<Movie> random = movieRepository.findRandom();
        resetGame(userId, game, random);
        return gameRepository.save(game);
    }

    private void resetGame(long userId, Game game, List<Movie> random) {
        game.setRound(1);
        game.setMovieOne(random.get(0).getId());
        game.setMovieTwo(random.get(1).getId());
        game.setUserId(userId);
        game.setCorrectAnswers(0);
        game.setWrongAnswers(0);
    }
}
