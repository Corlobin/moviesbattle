DROP TABLE IF EXISTS TBL_USER;
DROP TABLE IF EXISTS TBL_USER_PROFILE;
DROP TABLE IF EXISTS TBL_USER_USER_PROFILES;
DROP TABLE IF EXISTS TBL_GAME;

CREATE TABLE TBL_USER (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    username VARCHAR(128) NOT NULL,
    password VARCHAR(128) NOT NULL
);

CREATE TABLE TBL_MOVIE (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    title VARCHAR(128) NOT NULL,
    title_year VARCHAR(128) NOT NULL,
    imdb_id VARCHAR(128) NOT NULL,
    imdb_votes VARCHAR(128) NOT NULL,
    imdb_rating VARCHAR(128) NOT NULL
);

CREATE TABLE TBL_USER_PROFILE (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    name VARCHAR(128) NOT NULL
);

CREATE TABLE TBL_USER_USER_PROFILES (
    user_id INT NOT NULL,
    user_profiles_id INT NOT NULL
);

CREATE TABLE TBL_GAME (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    round INT NOT NULL,
    correct_answers INT NOT NULL,
    wrong_answers INT NOT NULL,
    max_ranking INT NOT NULL,
    movie_one INT NOT NULL,
    movie_two INT NOT NULL,
    user_id INT NOT NULL
);

CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1;
