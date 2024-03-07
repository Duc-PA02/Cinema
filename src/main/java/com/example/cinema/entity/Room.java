package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int capacity;
    private int type;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinemaid",foreignKey = @ForeignKey(name = "fk_room_cinema"))
    @JsonManagedReference
    private Cinema cinema;
    private String code;
    private String name;
    @Column(name = "isactive")
    private boolean isActive = true;
    @OneToMany(mappedBy = "roomId")
    @JsonBackReference
    private List<Schedule> roomschedulelist;
    @OneToMany(mappedBy = "roomId")
    @JsonBackReference
    private Set<Seat> roomseats;
}
