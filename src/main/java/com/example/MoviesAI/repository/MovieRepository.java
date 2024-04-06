package com.example.MoviesAI.repository;

import com.example.MoviesAI.entity.Movie;
import com.example.MoviesAI.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findByGenreOrderByRatingDesc(Genre genre);
}
