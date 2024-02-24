package com.example.cinema.repository;

import com.example.cinema.entity.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillStatusRepository extends JpaRepository<BillStatus, Integer> {
}
