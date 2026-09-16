package com.example.coding.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest (
        @NotBlank(message ="username bosh bolmasin")
        @Size(min=3,message = "username minal 3ta belgi bolishi kerak")
        String username,
        @NotBlank(message = "parol bosh bolmasligi kerak")
        @Size(min = 6,message = "parol kamida 6 ta belgidan iborat bolishi kerak") String password) {

}
