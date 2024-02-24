package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Table(name = "refreshtoken")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int token;
    @Column(name = "expiredtime")
    private LocalDate expireTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid",foreignKey = @ForeignKey(name = "fk_refreshtoken_user"))
    @JsonIgnore
    private User userId;
}