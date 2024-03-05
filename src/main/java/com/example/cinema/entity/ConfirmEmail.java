package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userid", foreignKey = @ForeignKey(name = "fk_ConfirmEmail_User"))
    @JsonManagedReference
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
