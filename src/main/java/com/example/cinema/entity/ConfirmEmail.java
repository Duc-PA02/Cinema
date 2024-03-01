package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "confirmemail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid",foreignKey = @ForeignKey(name = "fk_confirmemail_user"))
    @JsonIgnore
    private User userId;
    @Column(name = "requiredtime")
    private LocalDateTime requiredTime;
    @Column(name = "expiredtime")
    private LocalDateTime expiredTime;
    @Column(name = "comfirmcode")
    private String confirmCode;
    @Column(name = "iscomfirm")
    private boolean isComfirm;
}
