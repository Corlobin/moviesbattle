package com.letscode.moviesbattle.infra.database.repository;

import com.letscode.moviesbattle.infra.database.entity.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
    Optional<Game> findByUserId(long id);
    List<Game> findTop10ByOrderByMaxRanking();
}
