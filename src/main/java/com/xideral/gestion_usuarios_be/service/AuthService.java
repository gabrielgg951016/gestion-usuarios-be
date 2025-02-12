package com.xideral.gestion_usuarios_be.service;

import com.xideral.gestion_usuarios_be.dto.login.LoginRequest;
import com.xideral.gestion_usuarios_be.dto.token.TokenResponse;
import com.xideral.gestion_usuarios_be.entity.Token;
import com.xideral.gestion_usuarios_be.entity.UserAuth;
import com.xideral.gestion_usuarios_be.repository.TokenRepository;
import com.xideral.gestion_usuarios_be.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAuthRepository userAuthRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(LoginRequest loginRequest) {
        UserAuth.builder()
                .user(loginRequest.user())
                .password(passwordEncoder.encode(loginRequest.password()))
                .build();

        final UserAuth userAuthCreated = userAuthRepository.save(
                UserAuth.builder()
                        .user(loginRequest.user())
                        .password(passwordEncoder.encode(loginRequest.password()))
                        .build()
        );

        final String jwtToken = jwtService.generateToken(userAuthCreated);

        saveUserAuthToken(userAuthCreated, jwtToken);
        return TokenResponse.builder().accessToken(jwtToken).build();
    }

    public TokenResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.user(),
                        loginRequest.password())
        );

        final UserAuth userAuth = userAuthRepository.findByUser(loginRequest.user()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        final String jwtToken = jwtService.generateToken(userAuth);

        saveUserAuthToken(userAuth, jwtToken);

        return TokenResponse.builder().accessToken(jwtToken).build();
    }

    private void saveUserAuthToken(UserAuth userAuth, String jwtToken) {
        tokenRepository.save(
                Token.builder()
                        .userAuth(userAuth)
                        .token(jwtToken)
                        .expired(false)
                        .revoked(false)
                        .build()
        );
    }
}
