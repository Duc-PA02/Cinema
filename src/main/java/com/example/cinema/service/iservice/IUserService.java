package com.example.cinema.service.iservice;

import com.example.cinema.dto.UserDTO;
import org.springframework.data.domain.Page;

public interface IUserService {
    Page<UserDTO> getAllUser(int pageNum, int pageSize);
    UserDTO getUserById(int id);
}
