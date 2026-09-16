package com.example.coding.service;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtServiceTest {
     @Test
    void tokenYasab_usernameniQaytaraOlishiKerak(){
         JwtService jwtService = new JwtService();
         ReflectionTestUtils.setField(jwtService,"secret",
                 "bu-juda-uzun-maxfiy-kalit-kamida-32-belgi-bolsin-256bit");
         ReflectionTestUtils.setField(jwtService,"expiration",900000L);
         String token=jwtService.generateToken("elchin","USER");
         String username=jwtService.extractUsername(token);
         assertEquals("elchin",username);
     }



}
