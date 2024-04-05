package com.example.MoviesAI.repository;

import com.example.MoviesAI.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
