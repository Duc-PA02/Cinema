package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "promotion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int percent;
    private int quantity;
    private String type;
    @Column(name = "starttime")
    private LocalDate startTime;
    @Column(name = "endtime")
    private LocalDate endTime;
    private String description;
    private String name;
    @Column(name = "isactive")
    private boolean isActive;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rankcustomerid",foreignKey = @ForeignKey(name = "fk_promotion_rankcustomer"))
    @JsonIgnore
    private RankCustomer rankCustomerId;
    @OneToMany(mappedBy = "promotionId")
    @JsonIgnoreProperties
    private Set<Bill> probills;
}
