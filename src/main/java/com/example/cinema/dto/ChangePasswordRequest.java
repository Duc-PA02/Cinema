package com.example.cinema.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
//    private Integer id;
    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
