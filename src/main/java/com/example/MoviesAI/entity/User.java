package com.example.MoviesAI.entity;

import com.example.MoviesAI.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String email, password;

    @Enumerated(EnumType.STRING)
    UserRole userRole;

    @Column(name = "reset_token")
    String resetToken;

    @Column(name = "reset_token_expire_time")
    LocalDateTime resetTokenExpireTime;


    @OneToMany(mappedBy = "user")
    List<Favorite> favorites;
}
