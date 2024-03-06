package com.example.cinema.controller;

import com.example.cinema.dto.*;
import com.example.cinema.entity.ConfirmEmail;
import com.example.cinema.entity.User;
import com.example.cinema.exceptions.ConfirmEmailExpired;
import com.example.cinema.exceptions.DataNotFoundException;
import com.example.cinema.repository.ConfirmEmailRepository;
import com.example.cinema.repository.UserRepository;
import com.example.cinema.service.impl.AuthService;
import com.example.cinema.service.impl.ConfirmEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ConfirmEmailRepository confirmEmailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmEmailService confirmEmailService;
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
//    @PostMapping("user/change-password")
//    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
//        try {
//            String msg = authService.changePassword(changePasswordRequest);
//            return ResponseEntity.ok().body(msg);
//        }catch (DataNotFoundException ex){
//            return ResponseEntity.badRequest().body(ex.getMessage());
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
    @PostMapping("user/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userRepository.findById(user.getId()).isPresent()) {
                System.out.println("Nguoi dung la: " + user.getUserName());
                var result = authService.changePassword(user.getId(), request);
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không tìm thấy thông tin người dùng.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("user/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        try {
            String msg = authService.forgotPassword(forgotPasswordRequest);
            return ResponseEntity.ok().body(msg);
        }catch (DataNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping("user/confirm-new-password")
    public ResponseEntity<?> confirmNewPassword(@RequestBody ConfirmNewPasswordRequest confirmNewPasswordRequest){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            ConfirmEmail confirmEmail = confirmEmailRepository.findConfirmEmailByConfirmCode(confirmNewPasswordRequest.getConfirmCode());
            if (confirmEmail == null) {
                throw new DataNotFoundException("Không tìm thấy email xác nhận cho mã: " + confirmNewPasswordRequest.getConfirmCode());
            }
            Optional<User> userOptional = userRepository.findByEmail(confirmNewPasswordRequest.getEmail());
            if (userOptional == null) {
                throw new DataNotFoundException("Không tìm thấy người dùng cho email: " + confirmNewPasswordRequest.getEmail());
            }
            User user = userOptional.get();
            var isConfirm = confirmEmailService.confirmEmail(confirmNewPasswordRequest.getConfirmCode());
            if(isConfirm){
                user.setPassword(passwordEncoder.encode(confirmNewPasswordRequest.getNewPassword()));
                userRepository.save(user);
                confirmEmail.setUserId(null);
                confirmEmailRepository.delete(confirmEmail);
            }
            return ResponseEntity.ok().body("Tạo mật khẩu mới thành công");
        } catch (DataNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (ConfirmEmailExpired ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
