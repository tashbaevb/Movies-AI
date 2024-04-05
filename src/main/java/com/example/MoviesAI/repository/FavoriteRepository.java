package com.example.MoviesAI.repository;

import com.example.MoviesAI.entity.Favorite;
import com.example.MoviesAI.entity.Movie;
import com.example.MoviesAI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    Favorite findByUserAndMovie(User user, Movie movie);
}
