package com.letscode.moviesbattle.infra.gateway;

import com.letscode.moviesbattle.core.domain.RankingDomain;
import com.letscode.moviesbattle.core.gateway.RankingGateway;
import com.letscode.moviesbattle.infra.database.entity.Game;
import com.letscode.moviesbattle.infra.database.repository.GameRepository;
import com.letscode.moviesbattle.infra.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankingGatewayImpl implements RankingGateway {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Override
    public List<RankingDomain> getAll() {
        return gameRepository.findTop10ByOrderByMaxRanking()
            .stream()
            .map(game -> Pair.of(getUserName(game), game.getMaxRanking()))
            .map(pair -> new RankingDomain(pair.getLeft(), pair.getRight()))
            .collect(Collectors.toList());
    }

    private String getUserName(Game game) {
        return userRepository.findById(game.getUserId()).orElseThrow().getUsername();
    }
}
