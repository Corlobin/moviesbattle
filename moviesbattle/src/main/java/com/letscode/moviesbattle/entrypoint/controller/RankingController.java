package com.letscode.moviesbattle.entrypoint.controller;

import com.letscode.moviesbattle.core.usecase.RankingUseCase;
import com.letscode.moviesbattle.entrypoint.dto.RankingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingUseCase rankingUseCase;

    @GetMapping
    public List<RankingDTO> getAll() {
        return rankingUseCase.getAll()
            .stream()
            .map(rankingDomain -> new RankingDTO(rankingDomain.username(), rankingDomain.maxRanking()))
            .collect(Collectors.toList());
    }
}
