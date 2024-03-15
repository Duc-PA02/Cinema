package com.example.cinema.repository;

import com.example.cinema.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("SELECT s FROM Schedule s WHERE s.roomId.id = :roomId")
    List<Schedule> findByRoomId(@Param("roomId") int roomId);
    @Query(nativeQuery = true, value = "SELECT COUNT(s.id) " +
            "FROM Schedule s " +
            "WHERE s.roomid = ?1 " +
            "AND ((s.startAt BETWEEN ?2 AND ?3) OR (s.endAt BETWEEN ?2 AND ?3))")
    int countRoomScheduleConflict(int roomId, LocalDateTime startAt, LocalDateTime endAt);
    @Query(nativeQuery = true,value = "SELECT s.* FROM schedule s " +
            "JOIN room r ON s.roomid = r.id " +
            "JOIN cinema c ON r.cinemaid = c.id " +
            "WHERE s.movieid = ?1 " +
            "AND c.id = ?2 " +
            "AND r.id = ?3 ")
    List<Schedule> findByMovieIdAndRoomCinemaIdAndRoomId(int movie,int cinemaId,int roomId);

}
