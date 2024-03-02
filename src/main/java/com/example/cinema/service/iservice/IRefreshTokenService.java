package com.example.cinema.service.iservice;

import com.example.cinema.entity.RefreshToken;

public interface IRefreshTokenService {
    RefreshToken createRefreshToken (int userID) throws Exception;
}
