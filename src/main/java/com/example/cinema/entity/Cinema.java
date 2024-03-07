package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "cinema")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String address;
    private String description;
    private String code;
    @Column(name = "nameofcinema")
    private String nameOfCinema;
    @Column(name = "isactive")
    private boolean isActive = true;
    @OneToMany(mappedBy = "cinema")
    @JsonBackReference
    private Set<Room> cinemarooms;
}
