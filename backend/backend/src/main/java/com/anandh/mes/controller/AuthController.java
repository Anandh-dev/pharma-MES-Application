package com.anandh.mes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.AuthResponse;
import com.anandh.mes.dto.LoginRequest;
import com.anandh.mes.dto.UserDTO;
import com.anandh.mes.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody UserDTO dto) {

               userService.registerUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AuthResponse.builder()
                        .message("User Registered Successfully")
                        .build());

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        System.out.println("Step 1");

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()));

        System.out.println("Step 2");

        System.out.println(authentication);

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .message("Login Successful")
                        .build());
    }
}