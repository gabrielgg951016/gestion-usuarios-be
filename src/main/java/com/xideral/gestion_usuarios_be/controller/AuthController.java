package com.xideral.gestion_usuarios_be.controller;

import com.xideral.gestion_usuarios_be.dto.login.LoginRequest;
import com.xideral.gestion_usuarios_be.dto.token.TokenResponse;
import com.xideral.gestion_usuarios_be.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody final LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.register(loginRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody final LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
