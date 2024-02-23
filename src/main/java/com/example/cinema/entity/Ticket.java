package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scheduleid",foreignKey = @ForeignKey(name = "fk_ticket_schedule"))
    @JsonIgnore
    private Schedule scheduleId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seatid",foreignKey = @ForeignKey(name = "fk_ticket_seat"))
    @JsonIgnore
    private Seat seatId;
    @Column(name = "priceticket")
    private double priceTicket;
    @Column(name = "isactive")
    private boolean isActive;
    @OneToMany(mappedBy = "ticketId")
    @JsonIgnoreProperties
    private Set<BillTicket> ticketbills;
}
