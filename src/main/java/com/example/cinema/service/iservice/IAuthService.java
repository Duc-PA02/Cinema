package com.example.cinema.service.iservice;

import com.example.cinema.dto.LoginDTO;
import com.example.cinema.dto.LoginRequest;

public interface IAuthService {
    LoginDTO login(LoginRequest loginRequest) throws Exception;
}
