package com.example.cinema.controller;

import com.example.cinema.dto.LoginDTO;
import com.example.cinema.dto.LoginRequest;
import com.example.cinema.exceptions.DataNotFoundException;
import com.example.cinema.service.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("user/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            LoginDTO info = authService.login(loginRequest);
            return ResponseEntity.ok().body(info);
        } catch (DataNotFoundException e) {
            // Email không tồn tại
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email không tồn tại");
        } catch (AuthenticationException e) {
            // Sai mật khẩu hoặc thông tin đăng nhập không hợp lệ
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai mật khẩu");
        } catch (Exception e) {
            //lỗi khác do serve
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
