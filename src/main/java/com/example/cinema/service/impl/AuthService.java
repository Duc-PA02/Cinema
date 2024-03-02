package com.example.cinema.service.impl;

import com.example.cinema.component.JwtTokenUtils;
import com.example.cinema.dto.LoginDTO;
import com.example.cinema.dto.LoginRequest;
import com.example.cinema.dto.RegisterRequest;
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
}
