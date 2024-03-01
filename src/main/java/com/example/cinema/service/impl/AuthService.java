package com.example.cinema.service.impl;

import com.example.cinema.dto.RegisterRequest;
import com.example.cinema.entity.Role;
import com.example.cinema.entity.User;
import com.example.cinema.repository.RankCustomerRepository;
import com.example.cinema.repository.RoleRepository;
import com.example.cinema.repository.UserRepository;
import com.example.cinema.repository.UserStatusRepository;
import com.example.cinema.service.iservice.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserStatusRepository userStatusRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RankCustomerRepository rankCustomerRepository;
    @Autowired
    private UserRepository userRepository;

    public void saveUser(RegisterRequest registerRequest){
        Role userRole = roleRepository.findById(2).orElseThrow(()
                -> new IllegalStateException("Role not found user"));
        User user = User.builder()
                .point(0)
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .userName(registerRequest.getUserName())
                .isActive(true)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roleId(userRole)
                .rankCustomerId(rankCustomerRepository.findById(1).orElse(null))
                .userStatusId(userStatusRepository.findById(1).orElse(null))
                .build();
        userRepository.save(user);
    }
}
