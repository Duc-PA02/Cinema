package com.example.cinema.dto;

import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillDTO {
    private double totalMoney;
    private String tradingCode;
    private LocalDateTime creatTime;
    private LocalDateTime updateTime;
    private String name;
    private int customerId;
    private int promotionId;
    private int billStatusId;
}
