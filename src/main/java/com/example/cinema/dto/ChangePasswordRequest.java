package com.example.cinema.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private int id;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
