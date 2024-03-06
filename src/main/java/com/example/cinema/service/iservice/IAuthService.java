package com.example.cinema.service.iservice;

import com.example.cinema.dto.ChangePasswordRequest;
import com.example.cinema.dto.ForgotPasswordRequest;
import com.example.cinema.dto.LoginDTO;
import com.example.cinema.dto.LoginRequest;

public interface IAuthService {
    LoginDTO login(LoginRequest loginRequest) throws Exception;
    String changePassword(int userId, ChangePasswordRequest changePasswordRequest) throws Exception;
//    String changePassword(ChangePasswordRequest changePasswordRequest) throws Exception;

    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest)throws Exception;
}
