package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "cinema")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String adress;
    private String description;
    private String code;
    @Column(name = "nameofcinema")
    private String nameOfCinema;
    @Column(name = "isactive")
    private boolean isActive;
    @OneToMany(mappedBy = "cinemaId")
    @JsonIgnoreProperties
    private Set<Room> cinemarooms;
}
