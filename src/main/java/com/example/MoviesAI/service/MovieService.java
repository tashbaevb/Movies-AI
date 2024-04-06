package com.example.MoviesAI.service;

import com.example.MoviesAI.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<Movie> getAllMovies();

    Optional<Movie> getMovieById(Integer id);

    Movie createMovie(Movie movie);

    Movie updateMovie(Integer id, Movie movie);

    void deleteMovie(Integer id);

    List<Movie> getRecommend();
}
