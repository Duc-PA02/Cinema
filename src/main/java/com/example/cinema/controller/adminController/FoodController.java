package com.example.cinema.controller.adminController;

import com.example.cinema.dto.FoodCreateRequest;
import com.example.cinema.dto.FoodUpdateRequest;
import com.example.cinema.service.impl.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;
    @PostMapping("admin/food/create")
    public ResponseEntity<?> createFood(@RequestBody FoodCreateRequest foodCreateRequest){
        try {
            return ResponseEntity.ok().body(foodService.createFood(foodCreateRequest));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping("admin/food/update")
    public ResponseEntity<?> updateFood(@RequestBody FoodUpdateRequest foodUpdateRequest){
        try {
            return ResponseEntity.ok().body(foodService.updateFood(foodUpdateRequest));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @DeleteMapping("admin/food/delete")
    public ResponseEntity<?> deleteFood(@RequestParam int id){
        try {
            return ResponseEntity.ok().body(foodService.deleteFood(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("admin/food-bestsell7day")
    public ResponseEntity<?> getFoodBestSelling7Day(){
        try {
            return ResponseEntity.ok().body(foodService.getFoodBestSale7day());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
