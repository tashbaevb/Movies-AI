package com.example.MoviesAI.service.impl;

import com.example.MoviesAI.entity.Favorite;
import com.example.MoviesAI.entity.Movie;
import com.example.MoviesAI.entity.User;
import com.example.MoviesAI.repository.FavoriteRepository;
import com.example.MoviesAI.repository.MovieRepository;
import com.example.MoviesAI.repository.UserRepository;
import com.example.MoviesAI.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
