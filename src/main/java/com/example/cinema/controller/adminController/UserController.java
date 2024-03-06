package com.example.cinema.controller.adminController;

import com.example.cinema.dto.UserDTO;
import com.example.cinema.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("admin/get/all-user")
    public Page<UserDTO> getAllUser(int pageNum, int pageSize){
        return userService.getAllUser(pageNum, pageSize);
    }
    @GetMapping("admin/get/userbyid")
    public UserDTO getUserById(int id){
        return userService.getUserById(id);
    }
}
