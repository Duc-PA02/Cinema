package com.example.cinema.repository;

import com.example.cinema.entity.ConfirmEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmEmailRepository extends JpaRepository<ConfirmEmail, Integer> {
    ConfirmEmail findConfirmEmailByConfirmCode(String confirmCode);
}
