package com.example.cinema.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateRoomRequest {
    private int id;
    private int capacity;
    private int type;
    private String description;
    private String code;
    private String name;
    private int cinemaId;
}
