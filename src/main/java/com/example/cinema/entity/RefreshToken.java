package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.spi.Tokens;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "refreshtoken")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    @Column(name = "expiredtime")
    private LocalDateTime expiredTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid",foreignKey = @ForeignKey(name = "fk_refreshtoken_user"))
    @JsonIgnore
    private User userId;
}
