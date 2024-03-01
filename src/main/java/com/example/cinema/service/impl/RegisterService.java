package com.example.cinema.service.impl;


import com.example.cinema.dto.RegisterRequest;
import com.example.cinema.exceptions.DataIntegrityViolationException;
import com.example.cinema.repository.UserRepository;
import com.example.cinema.service.iservice.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService implements IRegisterService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmEmailService confirmEmailService;


    @Override
    public String register(RegisterRequest registerRequest) throws Exception {
        String email = registerRequest.getEmail();
        if (userRepository.existsByEmail(email)){
            throw new DataIntegrityViolationException("email đã tồn tại");
        }
        confirmEmailService.sendConfirmEmail(email);
        return "Mã xác minh đã được gửi đến email của bạn";
    }
}
