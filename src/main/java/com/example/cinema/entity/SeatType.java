package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "seattype")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nametype")
    private String nameType;
    @OneToMany(mappedBy = "seatTypeId")
    @JsonIgnoreProperties
    private Set<Seat> seattypes;
}
