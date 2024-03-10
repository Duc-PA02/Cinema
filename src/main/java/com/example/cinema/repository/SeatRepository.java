package com.example.cinema.repository;

import com.example.cinema.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    boolean existsByLineAndNumberAndIdNot(String line, int number, int id);
    List<Seat> findAllByNumberAndLine(int number, String line);
}
