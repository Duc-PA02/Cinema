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
@Table(name = "bill")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "totalmoney")
    private double totalMoney;
    @Column(name = "tradingcode")
    private String tradingCode;
    @Column(name = "creattime")
    private LocalDate creatTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid",foreignKey = @ForeignKey(name = "fk_bill_user"))
    @JsonIgnore
    private User customerId;
    private String name;
    @Column(name = "updatetime")
    private LocalDate updateTime;
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
