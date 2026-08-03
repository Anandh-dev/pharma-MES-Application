package com.anandh.mes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.JwtAuthResponse;
import com.anandh.mes.dto.LoginRequest;
import com.anandh.mes.dto.UserDTO;
import com.anandh.mes.security.JwtService;
import com.anandh.mes.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO dto) {

        userService.registerUser(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User Registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(
            @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()));

        UserDetails user =
                (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(user);

        String role = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

        JwtAuthResponse response =
                JwtAuthResponse.builder()
                        .accessToken(token)
                        .tokenType("Bearer")
                        .username(user.getUsername())
                        .role(role)
                        .build();

        return ResponseEntity.ok(response);
    }
}