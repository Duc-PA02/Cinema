package com.example.cinema.controller.homeController;

import com.example.cinema.service.impl.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FoodHomeController {
    private final FoodService foodService;
    @GetMapping("food/get-fooddto")
    public ResponseEntity<?> getMovies(){
        try {
            return ResponseEntity.ok().body(foodService.getFoodDTOs());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
