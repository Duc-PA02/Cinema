package com.example.cinema.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FoodUpdateRequest {
    private int id;
    private double price;
    private String description;
    private String image;
    private String nameOfFood;
}
