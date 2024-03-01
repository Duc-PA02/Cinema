package com.example.cinema.service.iservice;

import com.example.cinema.dto.RegisterRequest;

public interface IRegisterService {
    String register(RegisterRequest registerRequest) throws Exception;
}
