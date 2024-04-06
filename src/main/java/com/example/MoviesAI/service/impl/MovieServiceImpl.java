package com.example.MoviesAI.service.impl;

import com.example.MoviesAI.entity.Movie;
import com.example.MoviesAI.enums.Genre;
import com.example.MoviesAI.repository.MovieRepository;
import com.example.MoviesAI.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;


    @Override
    public List<Movie> getRecommend() {
        List<Movie> all = movieRepository.findAll();
        Map<Genre, Long> genreCountMap = all.stream()
                .collect(Collectors.groupingBy(Movie::getGenre, Collectors.counting()));

        // Найти жанр с наибольшим количеством фильмов
        Genre mostCommonGenre = genreCountMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        // Если нашли жанр, получаем все фильмы этого жанра из базы данных
        if (mostCommonGenre != null) {
            return movieRepository.findByGenreOrderByRatingDesc(mostCommonGenre);
        } else {
            // Если база данных пуста или все фильмы имеют разные жанры, вернуть пустой список
            return List.of();
        }

    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Optional<Movie> getMovieById(Integer id) {
        return movieRepository.findById(id);
    }

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Integer id, Movie movie) {
        if (movieRepository.existsById(id)) {
            movie.setId(id);
            return movieRepository.save(movie);
        } else {
            throw new RuntimeException("Movie with id " + id + " not found");
        }
    }

    @Override
    public void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
    }
}
