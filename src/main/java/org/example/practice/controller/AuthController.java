package org.example.practice.controller;

import lombok.RequiredArgsConstructor;
import org.example.practice.dto.LoginDto;
import org.example.practice.dto.RegisterDto;
import org.example.practice.service.AuthService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDto registerDto) {
        String register = authService.register(registerDto);
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto) {
        var login = authService.login(loginDto);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }
}
