package com.example.MoviesAI.service.impl;

import com.example.MoviesAI.entity.Favorite;
import com.example.MoviesAI.entity.Movie;
import com.example.MoviesAI.entity.User;
import com.example.MoviesAI.enums.Genre;
import com.example.MoviesAI.repository.FavoriteRepository;
import com.example.MoviesAI.repository.MovieRepository;
import com.example.MoviesAI.repository.UserRepository;
import com.example.MoviesAI.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public void addToFavorite(Integer userId, Integer movieId) {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            throw new RuntimeException("Movie not found");
        }

        Favorite existingFavorite = favoriteRepository.findByUserAndMovie(user, movie);
        if (existingFavorite == null) {
            Favorite newFavorite = new Favorite();
            newFavorite.setUser(user);
            newFavorite.setMovie(movie);
            favoriteRepository.save(newFavorite);
        } else {
            throw new RuntimeException("This movie is already dort");
        }
    }

    @Override
    public void removeFromFavorite(Integer userId, Integer movieId) {
        User user = userRepository.findById(userId).orElse(null);
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if (user != null && movie != null) {
            Favorite favorite = favoriteRepository.findByUserAndMovie(user, movie);

            if (favorite != null) {
                favoriteRepository.delete(favorite);
            }
        }
    }

    @Override
    public List<Movie> getUserFavorite(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        List<Movie> favoriteMovies = new ArrayList<>();

        if (user != null) {
            List<Favorite> favorites = user.getFavorites();
            for (Favorite favorite : favorites) {
                favoriteMovies.add(favorite.getMovie());
            }
        }

        return favoriteMovies;
    }

    @Override
    public List<Movie> getRecommendedMovies(Integer userId) {
        List<Movie> userFavorites = getUserFavorite(userId);

        Map<Genre, Long> genreCountMap = userFavorites.stream()
                .collect(Collectors.groupingBy(Movie::getGenre, Collectors.counting()));

        Genre mostCommonGenre = genreCountMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        if (mostCommonGenre != null) {
            List<Movie> recommendedMovies = movieRepository.findByGenreOrderByRatingDesc(mostCommonGenre).stream()
                    .filter(movie -> !userFavorites.contains(movie)) // Исключаем фильмы, которые уже есть в избранном
                    .collect(Collectors.toList());

            return recommendedMovies.stream()
                    .filter(movie -> movie.getYear() >= getMostRecentYear(userFavorites) || // Фильмы новее или такого же года, что и в избранном
                            movie.getGenre().equals(mostCommonGenre) || // Фильмы того же жанра, что и наиболее распространенный в избранном
                            movie.getRating() >= getAverageRating(userFavorites)) // Фильмы с рейтингом выше среднего рейтинга в избранном
                    .collect(Collectors.toList());
        } else {
            return List.of();
        }
    }

    private int getMostRecentYear(List<Movie> movies) {
        return movies.stream()
                .mapToInt(Movie::getYear)
                .max()
                .orElse(0);
    }

    // Метод для нахождения среднего рейтинга избранных фильмов пользователя
    private double getAverageRating(List<Movie> movies) {
        return movies.stream()
                .mapToDouble(Movie::getRating)
                .average()
                .orElse(0.0);
    }
}
