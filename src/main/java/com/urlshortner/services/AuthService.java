package com.urlshortner.services;

import com.urlshortner.JWTPayload.JWTRequest;
import com.urlshortner.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> login(JWTRequest jwtRequest);
    ResponseEntity<?> register(User user);

    ResponseEntity<?> validate(String jwtToken, String email);
}
