package com.example.cinema.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleDTO {
    private double price;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String name;
}
