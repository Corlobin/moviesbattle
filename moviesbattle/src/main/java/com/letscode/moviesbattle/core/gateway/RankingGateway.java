package com.letscode.moviesbattle.core.gateway;

import com.letscode.moviesbattle.core.domain.RankingDomain;

import java.util.List;

public interface RankingGateway {
    public List<RankingDomain> getAll();
}
