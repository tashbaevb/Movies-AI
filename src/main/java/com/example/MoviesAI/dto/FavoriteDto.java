package com.example.MoviesAI.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class FavoriteDto {

    Integer id;

    String title;

    Integer year;

    String genre;

    Double rating;
}
