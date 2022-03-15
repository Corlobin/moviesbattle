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
@Table(name = "TBL_MOVIE")
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;
    @Column(name = "title_year")
    private String year;
    @Column(name = "imdb_id")
    private String imdbId;
    @Column(name = "imdb_votes")
    private String imdbVotes;
    @Column(name = "imdb_rating")
    private String imdbRating;

    public double getPontuation() {
        return Double.parseDouble(imdbVotes) * Double.parseDouble(imdbRating);
    }
}
