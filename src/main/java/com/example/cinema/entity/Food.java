package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "food")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double price;
    private String description;
    private String image;
    @Column(name = "nameoffood")
    private String nameOfFood;
    @Column(name = "isactive")
    private boolean isActive;
    @OneToMany(mappedBy = "foodId")
    @JsonIgnoreProperties
    private Set<BillFood> foodbills;
}
