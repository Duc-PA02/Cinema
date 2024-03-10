package com.example.cinema.service.iservice;

import com.example.cinema.dto.FoodCreateRequest;
import com.example.cinema.dto.FoodUpdateRequest;
import com.example.cinema.entity.Food;

public interface IFoodService {
    Food createFood(FoodCreateRequest createFoodRequest) throws Exception;

    Food updateFood(FoodUpdateRequest updateFoodRequest) throws Exception;
    public String deleteFood(int id);
}
