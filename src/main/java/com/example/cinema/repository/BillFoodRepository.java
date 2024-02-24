package com.example.cinema.repository;

import com.example.cinema.entity.BillFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillFoodRepository extends JpaRepository<BillFood, Integer> {
}
