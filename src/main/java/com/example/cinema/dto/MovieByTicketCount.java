package com.example.cinema.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieByTicketCount {
    private int movieId;
    private String nameMovie;
    private Long ticketCount;
}
