package com.example.cinema.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatCreateRequest {
    private int number;
    private String line;
    private int seatStatusId;
    private int roomId;
    private int seatTypeId;
}
