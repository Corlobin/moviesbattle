package com.letscode.moviesbattle.infra.database.repository;

import com.letscode.moviesbattle.infra.database.entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    List<Movie> findAll();
    @Query("select m from Movie m order by RAND()")
    List<Movie> findRandom();
}
