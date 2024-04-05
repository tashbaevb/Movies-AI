package com.example.MoviesAI.controller;

import com.example.MoviesAI.entity.Movie;
import com.example.MoviesAI.entity.User;
import com.example.MoviesAI.repository.UserRepository;
import com.example.MoviesAI.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final UserRepository userRepository;
    private final FavoriteService favoriteService;


    @PostMapping("/favorite/add/{movieId}")
    public ResponseEntity<String> addToFavorites(@PathVariable Integer movieId, Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found: " + email));

        try {
            favoriteService.addToFavorite(user.getId(), movieId);
            return ResponseEntity.ok("Movie added to favorites.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/favorite/remove/{movieId}")
    public ResponseEntity<String> removeFromFavorites(@PathVariable Integer movieId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found: " + email));

        try {
            favoriteService.removeFromFavorite(user.getId(), movieId);
            return ResponseEntity.ok("Course removed from favorites.");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/favorite/get")
    public ResponseEntity<List<Movie>> getUserFavorites(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found: " + email));

        List<Movie> userFavorites = favoriteService.getUserFavorite(currentUser.getId());
        return ResponseEntity.ok(userFavorites);
    }
}
