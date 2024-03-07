package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "seat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int number;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seatstatusid",foreignKey = @ForeignKey(name = "fk_seatseatstatus"))
    @JsonIgnore
    private SeatStatus seatStatusId;
    private String line;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roomid",foreignKey = @ForeignKey(name = "fk_seat_room"))
    @JsonManagedReference
    private Room roomId;
    @Column(name = "isactive")
    private boolean isActive;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seattypeid",foreignKey = @ForeignKey(name = "fk_seat_seattype"))
    @JsonIgnore
    private SeatType seatTypeId;
    @OneToMany(mappedBy = "seatId")
    @JsonIgnoreProperties
    private Set<Ticket> seattickets;
}
