package com.pingpal.pingpal_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

import com.pingpal.pingpal_backend.dto.AuthRequestDto;
import com.pingpal.pingpal_backend.dto.AuthResponseDto;
import com.pingpal.pingpal_backend.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "Log in and receive a JWT token")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}