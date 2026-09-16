package com.example.coding.auth;

import com.example.coding.dto.AuthRequest;
import com.example.coding.dto.AuthResponse;
import com.example.coding.dto.LoginRequest;
import com.example.coding.dto.RefreshRequest;
import com.example.coding.service.AuthService;
import com.example.coding.service.JwtService;
import com.example.coding.service.TokenBlackListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final TokenBlackListService tokenBlackListService;
    public AuthController(AuthService authService, TokenBlackListService tokenBlackListService, JwtService jwtService) {
        this.authService = authService;
        this.tokenBlackListService = tokenBlackListService;
 this.jwtService = jwtService;
    }
    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody AuthRequest authRequest) {
        return authService.register(authRequest);
    }
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
@PostMapping("/refresh")
    public AuthResponse refresh(@Valid @RequestBody RefreshRequest refreshRequest) {
        return authService.refreshToken(refreshRequest);
}

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            long ttl = jwtService.getRemainingTime(token);
            tokenBlackListService.blacklist(token, ttl);
        }
        return ResponseEntity.ok(Map.of("mes" +
                "sage", "Chiqildi"));
    }

}
