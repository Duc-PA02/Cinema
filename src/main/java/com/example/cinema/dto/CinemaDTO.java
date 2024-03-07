package com.example.cinema.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CinemaDTO {
    private String address;
    private String description;
    private String  code;
    private String nameOfCinema;
}
