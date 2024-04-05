package com.example.MoviesAI.service;

import com.example.MoviesAI.entity.Movie;

import java.util.List;

public interface FavoriteService {

    void addToFavorite(Integer userId, Integer movieId);

    void removeFromFavorite(Integer userId, Integer movieId);

    List<Movie> getUserFavorite(Integer userId);
}
