package com.example.cinema.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FoodBestSell7Day {
    private double price;
    private String description;
    private String image;
    private String nameOfFood;
    private Long totalQuantity;
}
