package com.letscode.moviesbattle.core.domain;

public record GameDomain (long gameId, int round, int correctAnswers, int wrongAnswers, int maxRanking, long userId, long movieOne, long movieTwo) {
}
