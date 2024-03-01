package com.example.cinema.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String userName;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String retypePassword;
}
