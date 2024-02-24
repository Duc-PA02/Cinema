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
@Table(name = "schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double price;
    @Column(name = "startat")
    private LocalDate startAt;
    @Column(name = "endat")
    private LocalDate endAt;
    private String code;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movieid",foreignKey = @ForeignKey(name = "fk_schedule_movie"))
    @JsonIgnore
    private Movie movieId;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roomid",foreignKey = @ForeignKey(name = "fk_schedule_room"))
    @JsonIgnore
    private Room roomId;
    @Column(name = "isactive")
    private boolean isActive;
    @OneToMany(mappedBy = "scheduleId")
    @JsonIgnoreProperties
    private Set<Ticket> scheduletickets;
}