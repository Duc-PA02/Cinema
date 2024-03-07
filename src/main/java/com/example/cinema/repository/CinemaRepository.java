package com.example.cinema.repository;

import com.example.cinema.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
    Cinema findByCode(String code);
    boolean existsByAddress (String address);
    boolean existsByNameOfCinema(String nameOfCinema);
    List<Cinema> findAllByAddressAndIdNot(String address, int id);

    List<Cinema> findAllByNameOfCinemaAndIdNot(String nameOfCode, int id);

    List<Cinema> findAllByCodeAndIdNot(String code, int id);
}
