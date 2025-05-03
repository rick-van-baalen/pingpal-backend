package com.pingpal.pingpal_backend.controller;

import com.pingpal.pingpal_backend.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authManager;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() throws Exception {
        String token = "mocked.jwt.token";
        Authentication auth = Mockito.mock(Authentication.class);

        Mockito.when(authManager.authenticate(any())).thenReturn(auth);
        Mockito.when(jwtUtil.generateToken("dev_admin")).thenReturn(token);

        String requestJson = """
            {
              "username": "dev_admin",
              "password": "V9k!2x#PzB$eTm4W"
            }
            """;

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void login_shouldReturn401_whenCredentialsAreInvalid() throws Exception {
        Mockito.when(authManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        String requestJson = """
            {
                "username": "wrong_user",
                "password": "wrong_password"
            }
            """;

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.error").value("Unauthorized"));
    }

    @Test
    void login_shouldReturn400_whenBodyIsIncomplete() throws Exception {
        Mockito.when(authManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        String requestJson = """
            {
                "username": "wrong_user"
            }
            """;

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Bad Request"));
    }
}