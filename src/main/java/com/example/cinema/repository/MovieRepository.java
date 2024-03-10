package com.example.cinema.repository;

import com.example.cinema.dto.MovieByTicketCount;
import com.example.cinema.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query(nativeQuery = true,
            value = "SELECT m.id, m.name, COUNT(t.id) AS TicketCount " +
                    "FROM movie m " +
                    "JOIN schedule s ON m.id = s.movieid " +
                    "JOIN ticket t ON s.id = t.scheduleid " +
                    "GROUP BY m.id, m.name " +
                    "ORDER BY TicketCount DESC")
    List<MovieByTicketCount> findMoviesOrderByTicketCount();
}
