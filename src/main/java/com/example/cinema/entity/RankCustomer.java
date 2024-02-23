package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "rankcustomer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RankCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int point;
    private String description;
    private String name;
    @Column(name = "isactive")
    private boolean isActive;
    @OneToMany(mappedBy = "rankCustomerId")
    @JsonIgnoreProperties
    private List<Promotion> rankcustomerprolist;
    @OneToMany(mappedBy = "rankCustomerId")
    @JsonIgnoreProperties
    private List<User> rankcustomeruserlist;
}
