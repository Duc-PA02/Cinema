package com.example.cinema.dto;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillDTO {
    private double totalMoney;
    private String creatTime;
    private String nameBill;
    private String nameOfFood;
    private int quantityFood;
    private String nameRoom;
    private String nameMovie;
}
