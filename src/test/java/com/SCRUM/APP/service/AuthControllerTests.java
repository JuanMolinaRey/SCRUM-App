package com.SCRUM.APP.service;

import com.SCRUM.APP.controller.AuthController;
import com.SCRUM.APP.dtos.response.AuthResponse;
import com.SCRUM.APP.dtos.resquest.LoginRequest;
import com.SCRUM.APP.dtos.resquest.RegisterRequest;
import com.SCRUM.APP.model.ERole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AuthControllerTests {
    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest("username", "password");
        AuthResponse authResponse = new AuthResponse("token", ERole.USER);

        when(authService.login(any(LoginRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("token"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(ERole.USER.toString()));

        verify(authService, times(1)).login(any(LoginRequest.class));
    }

    @Test
    public void testRegister() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("username", "email@example.com", "password", ERole.USER);
        AuthResponse authResponse = new AuthResponse("token", ERole.USER);

        when(authService.register(any(RegisterRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("token"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(ERole.USER.toString()));

        verify(authService, times(1)).register(any(RegisterRequest.class));
    }
}