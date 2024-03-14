package com.example.cinema.repository;

import com.example.cinema.entity.Bill;
import com.example.cinema.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    Bill findByTradingCode(String tradingCode);
}
