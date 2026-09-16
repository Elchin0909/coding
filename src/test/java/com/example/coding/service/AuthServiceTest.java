package com.example.coding.service;

import com.example.coding.dto.AuthResponse;
import com.example.coding.dto.LoginRequest;
import com.example.coding.entity.Role;
import com.example.coding.entity.User;
import com.example.coding.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;



@ExtendWith(SpringExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private AuthService authService;
    @Test
    void login_togriParolbilan_qaytarishi_kerak() {
        User soxtaUser = new User();
        soxtaUser.setUsername("elchin");
        soxtaUser.setPassword("hashlangan_parol");
        soxtaUser.setRole(Role.USER);

        when(userRepository.findByUsername("elchin")).thenReturn(Optional.of(soxtaUser));
        when(passwordEncoder.matches("hashlangan_parol", "hashlangan_parol")).thenReturn(true);  // encode EMAS
        when(jwtService.generateToken("elchin", "USER")).thenReturn("soxta_access_token");
        when(jwtService.refreshToken("elchin")).thenReturn("soxta_refresh_token");

        LoginRequest loginRequest = new LoginRequest("elchin", "hashlangan_parol");
        AuthResponse authResponse = authService.login(loginRequest);

        assertNotNull(authResponse);
        assertEquals("soxta_access_token", authResponse.accessToken());
    }
    @Test
    void login_notogri_parolbilan_xatoqaytarishi_kerak(){
        User soxtaUser=new User();
        soxtaUser.setUsername("elchin");
        soxtaUser.setPassword("hashlangan_parol");
        soxtaUser.setRole(Role.USER);
        when(userRepository.findByUsername("elchin")).thenReturn(Optional.of(soxtaUser));
        when(passwordEncoder.matches("notogri","hashlangan_parol")).thenReturn(false);

        LoginRequest  loginRequest=new LoginRequest("elchin","notogri");
        assertThrows(Exception.class, ()->authService.login(loginRequest));
    }


}
