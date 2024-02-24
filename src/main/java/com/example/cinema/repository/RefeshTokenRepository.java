package com.example.cinema.repository;

import com.example.cinema.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefeshTokenRepository extends JpaRepository<RefreshToken, Integer> {
}
