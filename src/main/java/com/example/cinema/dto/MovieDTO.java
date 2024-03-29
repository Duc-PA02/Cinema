package com.example.cinema.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieDTO {
    private int movieDuration;
    private LocalDateTime premiereDate;
    private String description;
    private String director;
    private String image;
    private String heroImage;
    private String language;
    private String name;
    private String trailer;
}
