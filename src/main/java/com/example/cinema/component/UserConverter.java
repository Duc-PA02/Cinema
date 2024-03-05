package com.example.cinema.component;

import com.example.cinema.dto.UserDTO;
import com.example.cinema.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDTO entityToDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUsername());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }
}
