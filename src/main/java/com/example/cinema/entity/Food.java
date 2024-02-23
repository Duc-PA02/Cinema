package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "food")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
