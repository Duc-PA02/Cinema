package com.example.cinema.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDTO {
    private int capacity;
    private int type;
    private String description;
    private String name;
}
