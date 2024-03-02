package com.example.cinema.service.impl;

import com.example.cinema.component.JwtTokenUtils;
import com.example.cinema.dto.*;
import com.example.cinema.entity.RefreshToken;
import com.example.cinema.entity.Role;
import com.example.cinema.entity.User;
import com.example.cinema.exceptions.DataNotFoundException;
import com.example.cinema.repository.RankCustomerRepository;
import com.example.cinema.repository.RoleRepository;
import com.example.cinema.repository.UserRepository;
import com.example.cinema.repository.UserStatusRepository;
import com.example.cinema.service.iservice.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private ConfirmEmailService confirmEmailService;
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

    @Override
    public LoginDTO login(LoginRequest loginRequest) throws Exception {
        User existingUser = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
        if(existingUser == null) {
            throw new DataNotFoundException("Email không tồn tại");
        }
        if(existingUser.isActive() == false){
            throw new DisabledException("Tài khoản người dùng bị vô hiệu hóa");
        }

        //Chuyền email,password, role vào authenticationToken để xac thực ngươi dùng
        UsernamePasswordAuthenticationToken auToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword(),
                existingUser.getAuthorities()
        );
        //Xác thực người dùng (nếu xác thực không thành công VD: sai pass ) thì sẽ ném ra ngoại lệ
        authenticationManager.authenticate(auToken);

        //Lấy role của user
        User userDetails = (User) userDetailsService.loadUserByUsername(loginRequest.getEmail());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        //sinh ngẫu nhiên 1 token từ existingUser
        String token = jwtTokenUtils.generateToken(existingUser);

        RefreshToken refreshTokens = refreshTokenService.createRefreshToken(existingUser.getId());

        LoginDTO loginDTO= LoginDTO.builder()
                .id(existingUser.getId())
                .userName(existingUser.getName())
                .token(token)
                .refreshToken(refreshTokens.getToken())
                .roles(roles)
                .build();
        return loginDTO;
    }

    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest) throws Exception {
        User user = userRepository.findById(changePasswordRequest.getId()).orElse(null);
        if(user == null ){
            throw new DataNotFoundException("Người dùng không tồn tại");
        }
        if(changePasswordRequest.getOldPassword().equals(changePasswordRequest.getNewPassword())){
            return "Mật khẩu mới và mật khẩu cũ không được trùng nhau";
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(!bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())){
            return "Mật khẩu cũ không chính xác";
        }
        if(!changePasswordRequest.getConfirmPassword().equals(changePasswordRequest.getNewPassword())){
            return "Xác nhận mật khẩu không trùng khớp";
        }
        user.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return "Đổi mật khẩu thành công";
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws Exception {
        User user = userRepository.findByEmail(forgotPasswordRequest.getEmail()).orElse(null);
        if(user == null){
            throw new DataNotFoundException("Email không tồn tại");
        }

        confirmEmailService.sendConfirmEmail(forgotPasswordRequest.getEmail());
        return "Mã xác nhận đã được gửi vui lòng kiểm tra email";
    }
}
