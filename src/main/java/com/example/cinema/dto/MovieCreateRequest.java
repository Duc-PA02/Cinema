package com.example.cinema.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieCreateRequest {
    private int movieDuration;
    private String endTime;
    private String premiereDate;
    private String description;
    private String director;
    private String image;
    private String heroImage;
    private String language;
    private String name;
    private String trailer;
    private int movieTypeId;
    private int rateId;
}
