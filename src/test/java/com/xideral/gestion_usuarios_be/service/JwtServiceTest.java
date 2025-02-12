package com.xideral.gestion_usuarios_be.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.xideral.gestion_usuarios_be.entity.UserAuth;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private static final String USERNAME = "testUser";
    private static final String expectToken = "eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiIxMCIsIm5hbWUiOiJ4aWRlcmFsIiwic3ViIjoieGlkZXJhbCIsImlhdCI6MTcz"
            + "OTMxODY4MSwiZXhwIjoxNzM5NDA1MDgxfQ.3-8UrtTaKM_MWNf8mdcc_E2ieVMHJLKIPe6-zMfTRRSoR7Ve24WNyOQOrIMErZP4";

    @Mock
    private JwtService jwtService;

    @Test
    void generateToken_shouldGenerateValidToken() {

        UserAuth userAuth = UserAuth.builder()
                .id(1L)
                .user(USERNAME)
                .build();

        when(jwtService.generateToken(userAuth)).thenReturn(expectToken);

        String token = jwtService.generateToken(userAuth);

        assertNotNull(token);
    }

    @Test
    void isTokenValid_shouldReturnFalseForExpiredToken() {

        UserAuth userAuth = UserAuth.builder()
                .id(1L)
                .user(USERNAME)
                .build();

        String token = Jwts.builder()
                .subject(USERNAME)
                .issuedAt(Date.from(Instant.now().minus(1, ChronoUnit.HOURS)))
                .expiration(Date.from(Instant.now().minus(30, ChronoUnit.MINUTES)))
                .compact();

        boolean isValid = jwtService.isTokenValid(token, userAuth);

        assertFalse(isValid);
    }

    @Test
    void isTokenValid_shouldReturnFalseForInvalidUser() {

        UserAuth userAuth = UserAuth.builder()
                .id(1L)
                .user("user")
                .build();

        boolean isValid = jwtService.isTokenValid(expectToken, userAuth);

        assertFalse(isValid);
    }

}
