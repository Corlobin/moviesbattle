package com.letscode.moviesbattle.core.usecase;

import com.letscode.moviesbattle.core.domain.RankingDomain;
import com.letscode.moviesbattle.core.gateway.RankingGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingUseCase {
    private final RankingGateway rankingGateway;

    public List<RankingDomain> getAll() {
        return rankingGateway.getAll();
    }

}
