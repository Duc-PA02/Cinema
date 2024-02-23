package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Table(name = "confirmemail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid",foreignKey = @ForeignKey(name = "fk_confirmemail_user"))
    @JsonIgnore
    private User userId;
    @Column(name = "requiredtime")
    private LocalDate requiredTime;
    @Column(name = "expiredtime")
    private LocalDate expiredTime;
    @Column(name = "comfirmcode")
    private String confirmCode;
    @Column(name = "iscomfirm")
    private boolean isComfirm;
}
