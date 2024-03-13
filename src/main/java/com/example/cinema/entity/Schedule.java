package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double price;
    @Column(name = "startat")
    private LocalDateTime startAt;
    @Column(name = "endat")
    private LocalDateTime endAt;
    private String code;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movieid",foreignKey = @ForeignKey(name = "fk_schedule_movie"))
    @JsonIgnore
    private Movie movieId;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roomid",foreignKey = @ForeignKey(name = "fk_schedule_room"))
    @JsonManagedReference
    private Room roomId;
    @Column(name = "isactive")
    private boolean isActive = true;
    @OneToMany(mappedBy = "scheduleId")
    @JsonIgnoreProperties
    private Set<Ticket> scheduletickets;
}
