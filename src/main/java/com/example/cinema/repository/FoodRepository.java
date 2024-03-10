package com.example.cinema.repository;

import com.example.cinema.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    boolean existsByNameOfFood(String nameOfFood);

    boolean existsByNameOfFoodAndIdNot(String nameOfFood, int foodId);
}
