package com.example.cinema.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "generalsetting")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "breaktime")
    private LocalDateTime breakTime;
    @Column(name = "businesshours")
    private int businessHours;
    @Column(name = "closetime")
    private LocalDateTime closeTime;
    @Column(name = "fixedticketprice")
    private double fixedTicketPrice;
    @Column(name = "percentday")
    private int percentDay;
    @Column(name = "percentweekend")
    private int percentWeekend;
    @Column(name = "timebegintochange")
    private LocalDateTime timeBeginToChange;
}
