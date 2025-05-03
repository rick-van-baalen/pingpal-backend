package com.pingpal.pingpal_backend.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();

        // Set the secret key manually for testing (base64-encoded)
        String base64Secret = "5zuPz4a1yOpH+eBHKZUzNcvnlfZ92Lb9ccGc5Xj4yZw="; // same as in application.properties
        jwtUtil.setSecretForTest(base64Secret); // This method we'll define below
        jwtUtil.setExpirationMsForTest(3600000); // 1 hour
        jwtUtil.init(); // manually initialize signing key
    }

    @Test
    void generateToken_shouldReturnValidToken() {
        String token = jwtUtil.generateToken("testuser");

        assertNotNull(token);
        assertEquals("testuser", jwtUtil.extractUsername(token));
        assertTrue(jwtUtil.isTokenValid(token, "testuser"));
    }

    @Test
    void isTokenValid_shouldReturnFalseForWrongUsername() {
        String token = jwtUtil.generateToken("alice");

        assertFalse(jwtUtil.isTokenValid(token, "bob"));
    }

    @Test
    void extractUsername_shouldMatch() {
        String token = jwtUtil.generateToken("dev_admin");

        String extracted = jwtUtil.extractUsername(token);
        assertEquals("dev_admin", extracted);
    }

    @Test
    void isTokenValid_shouldReturnFalse_whenTokenIsExpired() throws InterruptedException {
        jwtUtil.setExpirationMsForTest(1000); // 1 second
        jwtUtil.init();

        String token = jwtUtil.generateToken("testuser");

        // Wait for the token to expire
        Thread.sleep(1500);

        boolean valid = jwtUtil.isTokenValid(token, "testuser");
        assertFalse(valid, "Token should be expired and thus invalid");
    }
}