package com.example.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CinemaRevenueDTO {
    private int cinemaId;
    private String cinemaName;
    private BigDecimal totalRevenue;
}
