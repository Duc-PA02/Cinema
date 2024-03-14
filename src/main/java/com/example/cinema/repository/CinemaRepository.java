package com.example.cinema.repository;

import com.example.cinema.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    Cinema findByNameOfCinema(String name);
    @Query(nativeQuery = true, value = "SELECT c.id, c.nameofcinema, SUM(bt.quantity * t.priceticket) AS total_revenue " +
            "FROM cinema c " +
            "JOIN room r ON c.id = r.cinemaid " +
            "JOIN schedule s ON r.id = s.roomid " +
            "JOIN ticket t ON s.id = t.scheduleid " +
            "JOIN billticket bt ON t.id = bt.ticketid " +
            "JOIN bill b ON bt.billid = b.id " +
            "WHERE b.creattime BETWEEN :startTime AND :endTime " +
            "GROUP BY c.id, c.nameofcinema")
    List<Object[]> findCinemaRevenueByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}
