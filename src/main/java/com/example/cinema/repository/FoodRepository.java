package com.example.cinema.repository;

import com.example.cinema.dto.FoodBestSell7Day;
import com.example.cinema.dto.FoodDTO;
import com.example.cinema.entity.Food;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    boolean existsByNameOfFood(String nameOfFood);

    boolean existsByNameOfFoodAndIdNot(String nameOfFood, int foodId);
    @Query(nativeQuery = true, value = "SELECT f.nameoffood, f.image, f.description, f.price, SUM(bf.quantity) AS total_quantity" +
            " FROM food f" +
            " JOIN billfood bf ON f.id = bf.foodid" +
            " JOIN bill b ON bf.billid = b.id" +
            " WHERE b.creattime >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)" +
            " GROUP BY f.id, f.nameoffood" +
            " ORDER BY total_quantity DESC" +
            " LIMIT 10;")
    List<Object[]> findBestSellingFoodsInLast7Days();
}
