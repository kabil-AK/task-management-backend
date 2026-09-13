package com.example.taskmanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.taskmanagement.dto.AuthRequest;
import com.example.taskmanagement.dto.RegisterRequest;
import com.example.taskmanagement.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        String response = authService.register(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody AuthRequest request) {

        String response = authService.login(request);

        return ResponseEntity.ok(response);
    }
}