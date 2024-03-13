package com.example.cinema.service.iservice;

import com.example.cinema.dto.FoodCreateRequest;
import com.example.cinema.dto.FoodDTO;
import com.example.cinema.dto.FoodUpdateRequest;
import com.example.cinema.entity.Food;

import java.util.List;

public interface IFoodService {
    Food createFood(FoodCreateRequest createFoodRequest) throws Exception;

    Food updateFood(FoodUpdateRequest updateFoodRequest) throws Exception;
    public String deleteFood(int id);
    List<FoodDTO> getFoodDTOs() throws Exception;
}
