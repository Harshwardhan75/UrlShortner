package com.urlshortner.controller;

import com.urlshortner.JWTPayload.JWTRequest;
import com.urlshortner.entity.User;
import com.urlshortner.exception.ErrorsException;
import com.urlshortner.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JWTRequest jwtRequest) {
        return authService.login(jwtRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ErrorsException(result);
        }
        return authService.register(user);
    }

    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestBody Map<String,Object> data) {
        return this.authService.validate(data.get("JWTToken").toString(), data.get("Email").toString());
    }

}
