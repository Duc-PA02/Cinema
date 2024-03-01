package com.example.cinema.controller;


import com.example.cinema.dto.RegisterRequest;
import com.example.cinema.entity.ConfirmEmail;
import com.example.cinema.repository.ConfirmEmailRepository;
import com.example.cinema.service.impl.AuthService;
import com.example.cinema.service.impl.ConfirmEmailService;
import com.example.cinema.service.impl.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private ConfirmEmailService confirmEmailService;
    @Autowired
    private ConfirmEmailRepository confirmEmailRepository;
    @Autowired
    private AuthService authService;

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        try {
            String msg = registerService.register(registerRequest);
            return ResponseEntity.ok().body(msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/user/confirm-register")
    public ResponseEntity<?> confirmRegister(@RequestParam String confirmCode, @RequestBody RegisterRequest registerRequest){
        try {
            boolean isConfirm = confirmEmailService.confirmEmail(confirmCode);
            ConfirmEmail confirmEmail = confirmEmailRepository.findConfirmEmailByConfirmCode(confirmCode);
            if (isConfirm){
                authService.saveUser(registerRequest);
                confirmEmailRepository.delete(confirmEmail);
            }
            return ResponseEntity.ok().body("Đăng ký thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
