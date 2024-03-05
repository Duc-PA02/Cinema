package com.example.cinema.service.impl;

import com.example.cinema.component.UserConverter;
import com.example.cinema.dto.UserDTO;
import com.example.cinema.repository.UserRepository;
import com.example.cinema.service.iservice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;
    @Override
    public Page<UserDTO> getAllUser(int pageNum, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        return userRepository
                .findAll(pageRequest)
                .map(x -> userConverter.entityToDTO(x));
    }

    @Override
    public UserDTO getUserById(int id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Người dùng này không có trên hệ thống"));
        UserDTO userDTO = userConverter.entityToDTO(user);
        return userDTO;
    }
}
