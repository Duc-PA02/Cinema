package com.example.cinema.service.impl;

import com.example.cinema.dto.FoodBestSell7Day;
import com.example.cinema.dto.FoodCreateRequest;
import com.example.cinema.dto.FoodDTO;
import com.example.cinema.dto.FoodUpdateRequest;
import com.example.cinema.entity.BillFood;
import com.example.cinema.entity.Food;
import com.example.cinema.exceptions.DataNotFoundException;
import com.example.cinema.repository.BillFoodRepository;
import com.example.cinema.repository.FoodRepository;
import com.example.cinema.service.iservice.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class FoodService implements IFoodService {
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private BillFoodRepository billFoodRepository;

    @Override
    public Food createFood(FoodCreateRequest createFoodRequest) throws Exception {
        if(foodRepository.existsByNameOfFood(createFoodRequest.getNameOfFood())){
            throw  new DataIntegrityViolationException("Food da ton tai");
        }

        Food food = Food.builder()
                .nameOfFood(createFoodRequest.getNameOfFood())
                .image(createFoodRequest.getImage())
                .price(createFoodRequest.getPrice())
                .isActive(true)
                .description(createFoodRequest.getDescription())
                .build();
        foodRepository.save(food);

        return food;
    }

    @Override
    public Food updateFood(FoodUpdateRequest updateFoodRequest) throws Exception {
        Food food = foodRepository.findById(updateFoodRequest.getId()).orElse(null);
        if(food == null){
            throw new DataNotFoundException("Food khong ton tai");
        }
        if (foodRepository.existsByNameOfFoodAndIdNot(updateFoodRequest.getNameOfFood(), updateFoodRequest.getId())){
            throw  new DataIntegrityViolationException("name da ton tai");
        }
        food.setNameOfFood(updateFoodRequest.getNameOfFood());
        food.setPrice(updateFoodRequest.getPrice());
        food.setDescription(updateFoodRequest.getDescription());
        food.setImage(updateFoodRequest.getImage());
        foodRepository.save(food);

        return food;
    }

    @Override
    public String deleteFood(int id) {
        Optional<Food> food = foodRepository.findById(id);
        if (food.isEmpty()){
            return "Khong tim thay food";
        }
        for (BillFood billFood : billFoodRepository.findAll()){
            if (billFood.getFoodId().getId()==id){
                billFoodRepository.delete(billFood);
            }
        }
        foodRepository.delete(food.get());
        return "Xoa thanh cong cinema";
    }

    @Override
    public List<FoodDTO> getFoodDTOs() throws Exception {
        List<Food> foods = foodRepository.findAll();
        if (foods.isEmpty()){
            throw new DataNotFoundException("Khong tim thay do an");
        }
        List<FoodDTO> foodDTOs = new ArrayList<>();
        for (Food food : foods){
            FoodDTO foodDTO = FoodDTO.builder()
                    .nameOfFood(food.getNameOfFood())
                    .description(food.getDescription())
                    .image(food.getImage())
                    .price(food.getPrice())
                    .build();
            foodDTOs.add(foodDTO);
        }
        return foodDTOs;
    }

    @Override
    public List<FoodBestSell7Day> getFoodBestSale7day() throws Exception {
        List<Object[]> foodData = foodRepository.findBestSellingFoodsInLast7Days();
        List<FoodBestSell7Day> foodDTOs = new ArrayList<>();

        if (foodData.isEmpty()) {
            throw new DataNotFoundException("Khong co mon an nao duoc ban trong 7 ngay qua");
        }

        for (Object[] row : foodData) {
            FoodBestSell7Day foodDTO = new FoodBestSell7Day();
            foodDTO.setNameOfFood((String) row[0]);
            foodDTO.setImage((String) row[1]);
            foodDTO.setDescription((String) row[2]);
            foodDTO.setPrice((Double) row[3]);
            foodDTO.setTotalQuantity(((BigDecimal) row[4]).longValue());
            foodDTOs.add(foodDTO);
        }
        return foodDTOs;
    }
}
