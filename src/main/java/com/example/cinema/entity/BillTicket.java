package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "billticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "billid",foreignKey = @ForeignKey(name = "fk_billticket_bill"))
    @JsonIgnore
    private Bill billId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticketid",foreignKey = @ForeignKey(name = "fk_billticket_ticket"))
    @JsonIgnore
    private Ticket ticketId;
}
