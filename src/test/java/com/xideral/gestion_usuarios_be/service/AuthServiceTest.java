package com.xideral.gestion_usuarios_be.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.xideral.gestion_usuarios_be.dto.login.LoginRequest;
import com.xideral.gestion_usuarios_be.dto.token.TokenResponse;
import com.xideral.gestion_usuarios_be.entity.UserAuth;
import com.xideral.gestion_usuarios_be.repository.TokenRepository;
import com.xideral.gestion_usuarios_be.repository.UserAuthRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserAuthRepository userAuthRepository;

    @InjectMocks
    private AuthService authService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void register_Success() {

        LoginRequest loginRequest = LoginRequest.builder()
                .user("testUser")
                .password("encodedPassword")
                .build();

        UserAuth expectedUserAuth = UserAuth.builder()
                .user("testUser")
                .password("encodedPassword")
                .build();

        when(passwordEncoder.encode(loginRequest.password())).thenReturn("encodedPassword");
        when(userAuthRepository.save(expectedUserAuth)).thenReturn(expectedUserAuth);
        when(jwtService.generateToken(expectedUserAuth)).thenReturn("testToken");

        TokenResponse response = authService.register(loginRequest);

        assertEquals("testToken", response.accessToken());

        verify(userAuthRepository, times(1)).save(expectedUserAuth);
        verify(jwtService, times(1)).generateToken(expectedUserAuth);
    }

    @Test
    void login_Success() {

        LoginRequest loginRequest = LoginRequest.builder()
                .user("testUser")
                .password("encodedPassword")
                .build();

        UserAuth expectedUserAuth = UserAuth.builder()
                .user("testUser")
                .password("encodedPassword")
                .build();

        when(userAuthRepository.findByUser("testUser")).thenReturn(java.util.Optional.of(expectedUserAuth));
        when(jwtService.generateToken(expectedUserAuth)).thenReturn("testToken");

        TokenResponse response = authService.login(loginRequest);

        assertEquals("testToken", response.accessToken());

        verify(userAuthRepository, times(1)).findByUser("testUser");
        verify(jwtService, times(1)).generateToken(expectedUserAuth);
    }

    @Test
    void login_Failed() {

        LoginRequest loginRequest = LoginRequest.builder()
                .user("invalidUser")
                .password("encodedPassword")
                .build();

        when(userAuthRepository.findByUser("invalidUser")).thenReturn(java.util.Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.login(loginRequest));

        verify(userAuthRepository, times(1)).findByUser("invalidUser");
        verify(jwtService, times(0)).generateToken(any(UserAuth.class));
    }

}
