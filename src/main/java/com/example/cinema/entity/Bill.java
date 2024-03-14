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
@Table(name = "bill")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "totalmoney")
    private double totalMoney;
    @Column(name = "tradingcode")
    private String tradingCode;
    @Column(name = "creattime")
    private LocalDateTime creatTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid",foreignKey = @ForeignKey(name = "fk_bill_user"))
    @JsonManagedReference
    private User customerId;
    private String name;
    @Column(name = "updatetime")
    private LocalDateTime updateTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotionid",foreignKey = @ForeignKey(name = "fk_bill_promotion"))
    @JsonIgnore
    private Promotion promotionId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "billstatusid",foreignKey = @ForeignKey(name = "fk_bill_billstatus"))
    @JsonIgnore
    private BillStatus billStatusId;
    @Column(name = "isactive")
    private boolean isActive;
    @OneToMany(mappedBy = "billId")
    @JsonIgnoreProperties
    private Set<BillFood> billFoods;
    @OneToMany(mappedBy = "billId")
    @JsonIgnoreProperties
    private Set<BillTicket> billTickets;
}
