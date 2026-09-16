package com.example.coding.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlackListService {
private final StringRedisTemplate redisTemplate;
public TokenBlackListService(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
}
public void blacklist(String token,long ttlMillis) {
    redisTemplate.opsForValue().set(
            "blacklist:"+token,
            "1",
            Duration.ofMillis(ttlMillis)
    );}

public boolean isBlacklisted(String token){
    return redisTemplate.hasKey("blacklist:"+token);
    }

}




