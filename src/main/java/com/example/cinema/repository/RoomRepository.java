package com.example.cinema.repository;

import com.example.cinema.entity.Cinema;
import com.example.cinema.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    boolean existsByName(String name);
    Room findByCode(String code);
}
