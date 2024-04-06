package com.example.MoviesAI.entity;

import com.example.MoviesAI.enums.Genre;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String title;

    Integer year;

    @Enumerated(EnumType.STRING)
    Genre genre;

    Double rating;
}
