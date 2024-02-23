package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "seatstatus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    @Column(name = "namestatus")
    private String nameStatus;
    @OneToMany(mappedBy = "seatStatusId")
    @JsonIgnoreProperties
    private Set<Seat> seatstts;
}
