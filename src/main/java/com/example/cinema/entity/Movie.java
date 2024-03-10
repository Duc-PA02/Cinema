package com.example.cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movie")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "movieduration")
    private int movieDuration;
    @Column(name = "endtime")
    private LocalDateTime endTime;
    @Column(name = "premieredate")
    private LocalDateTime premiereDate;
    private String description;
    private String director;
    private String image;
    @Column(name = "heroimage")
    private String heroImage;
    private String language;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movietypeid",foreignKey = @ForeignKey(name = "fk_movie_movietype"))
    @JsonIgnore
    private MovieType movieTypeId;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rateid",foreignKey = @ForeignKey(name = "fk_movie_rate"))
    @JsonIgnore
    private Rate rateId;
    private String trailer;
    @Column(name = "isactive")
    private boolean isActive;
    @OneToMany(mappedBy = "movieId")
    @JsonIgnoreProperties
    private List<Schedule> movieschedulelist;
}
