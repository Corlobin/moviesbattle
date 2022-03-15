package com.letscode.moviesbattle.infra.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_GAME")
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "round")
    private int round;

    @Column(name = "correct_answers")
    private int correctAnswers;

    @Column(name = "wrong_answers")
    private int wrongAnswers;

    @Column(name = "max_ranking")
    private int maxRanking;

    @Column(name = "movie_one")
    private long movieOne;

    @Column(name = "movie_two")
    private long movieTwo;

    @Column(name = "user_id")
    private long userId;

    public void incrementCorrectAnswers() {
        this.correctAnswers += 1;
        this.round += 1;
    }

    public void decrementCorrectAnswers() {
        this.wrongAnswers += 1;
        this.round += 1;
    }

    public void updateMaxRanking() {
        if (this.round > this.maxRanking) {
            this.maxRanking = round;
        }
    }
}
