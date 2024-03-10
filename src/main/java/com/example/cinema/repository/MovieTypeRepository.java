package com.example.cinema.repository;

import com.example.cinema.entity.Movie;
import com.example.cinema.entity.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTypeRepository extends JpaRepository<MovieType, Integer> {
}
