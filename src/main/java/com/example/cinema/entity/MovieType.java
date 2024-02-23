package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movietype")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "movietypename")
    private String movieTypeName;
    private boolean isActive;
    @OneToMany(mappedBy = "movieTypeId")
    @JsonIgnoreProperties
    private List<Movie> movieList;
}
