package com.example.coding.service;

import com.example.coding.dto.AuthRequest;
import com.example.coding.dto.AuthResponse;
import com.example.coding.dto.LoginRequest;
import com.example.coding.dto.RefreshRequest;
import com.example.coding.entity.Role;
import com.example.coding.entity.User;
import com.example.coding.exception.NotFoundException;
import com.example.coding.repo.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
            this.jwtService = jwtService;
    }
    public AuthResponse register(AuthRequest authRequest) {
        if(userRepository.findByUsername(authRequest.username()).isPresent()){
            throw new IllegalArgumentException("bu username band");

        }
        User user = new User();
        user.setUsername(authRequest.username());
        user.setPassword(passwordEncoder.encode(authRequest.password()));
        user.setRole(Role.USER);
        userRepository.save(user);
        String accessToken= jwtService.generateToken(user.getUsername(),user.getRole().name());
        String refreshToken= jwtService.refreshToken(user.getUsername());
        return new AuthResponse(accessToken,refreshToken);
    }
    public AuthResponse login(LoginRequest loginRequest) {
        User user=userRepository.findByUsername(loginRequest.username())
                .filter((u->passwordEncoder.matches(loginRequest.password(),u.getPassword())))
                .orElseThrow(()->new BadCredentialsException("username yoki parol notog`ri kiritildi"));
        String accessToken= jwtService.generateToken(user.getUsername(),user.getRole().name());
        String refreshToken= jwtService.refreshToken(user.getUsername());
        return new AuthResponse(accessToken,refreshToken);
    }
    public AuthResponse refreshToken(RefreshRequest refreshRequest) {
        String refreshToken= refreshRequest.refreshToken();
        String type= jwtService.extractType(refreshToken);
        if(!"refresh".equals(type)){
            throw new BadCredentialsException("refresh token band");
        }
        String username = jwtService.extractUsername(refreshToken);
        if(!jwtService.isValid(refreshToken,username)){
            throw new BadCredentialsException("refresh token yaroqsiz muddati tugagan");
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new NotFoundException("username topilmadi"));

String newAccessToken= jwtService.generateToken(user.getUsername(),user.getRole().name());
return new AuthResponse(newAccessToken,refreshToken);
    }
}

