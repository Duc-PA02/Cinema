package com.example.cinema.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {
    private int id;
    private String userName;
    private String email;
    private String token;
    private String refreshToken;
    private List<String> roles;
}
