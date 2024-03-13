package com.example.cinema.dto;

import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleUpdateRequest {
    private int id;
    private double price;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String name;
    private String code;
    private int movieId;
    private int roomId;
}
