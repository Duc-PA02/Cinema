package com.example.cinema.repository;

import com.example.cinema.entity.RankCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankCustomerRepository extends JpaRepository<RankCustomer, Integer> {
}
