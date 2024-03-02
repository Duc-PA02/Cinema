package com.example.cinema.service.impl;

import com.example.cinema.entity.RefreshToken;
import com.example.cinema.entity.User;
import com.example.cinema.exceptions.DataNotFoundException;
import com.example.cinema.repository.RefreshTokenRepository;
import com.example.cinema.repository.UserRepository;
import com.example.cinema.service.iservice.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService implements IRefreshTokenService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Value("${jwt.expirationRefreshToken}")
    private int expirationRefreshToken;
    @Override
    public RefreshToken createRefreshToken(int userID) throws Exception {
        User user = userRepository.findById(userID).orElse(null);
        if (user == null){
            throw new DataNotFoundException("Người dùng không tồn tại");
        }
        if (user.isActive()==false){
            throw new Exception("Tài khoản người dùng bị vô hiệu hóa");
        }
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiredTime(LocalDateTime.now().plusSeconds(expirationRefreshToken))
                .userId(user)
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
}
