package com.pingpal.pingpal_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pingpal.pingpal_backend.dto.RequestTemplateCreateDto;
import com.pingpal.pingpal_backend.dto.RequestTemplateUpdateDto;
import com.pingpal.pingpal_backend.model.RequestTemplate;
import com.pingpal.pingpal_backend.repository.RequestTemplateRepository;
import com.pingpal.pingpal_backend.util.JwtUtil;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@WebMvcTest(RequestTemplateController.class)
@AutoConfigureMockMvc(addFilters = false)
class RequestTemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RequestTemplateRepository requestRepo;

    @MockBean
    private JwtUtil jwtUtil;
    
    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void create_shouldReturn201_whenRequestIsValid() throws Exception {
        RequestTemplateCreateDto dto = new RequestTemplateCreateDto();
        dto.setName("My Test Request");
        dto.setMethod("GET");
        dto.setUrl("https://api.example.com/data");

        RequestTemplate saved = new RequestTemplate();
        saved.setName(dto.getName());
        saved.setMethod(dto.getMethod());
        saved.setUrl(dto.getUrl());

        Mockito.when(requestRepo.save(any())).thenReturn(saved);

        mockMvc.perform(post("/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("My Test Request"))
            .andExpect(jsonPath("$.method").value("GET"))
            .andExpect(jsonPath("$.url").value("https://api.example.com/data"));
    }

    @Test
    void create_shouldReturn400_whenNameIsMissing() throws Exception {
        RequestTemplateCreateDto dto = new RequestTemplateCreateDto();

        mockMvc.perform(post("/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void update_shouldPatchOnlyProvidedFields() throws Exception {
        UUID id = UUID.randomUUID();

        RequestTemplate existing = new RequestTemplate();
        existing.setName("Original");
        existing.setMethod("GET");
        existing.setUrl("https://original.url");

        Mockito.when(requestRepo.findById(id)).thenReturn(Optional.of(existing));
        Mockito.when(requestRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        RequestTemplateUpdateDto dto = new RequestTemplateUpdateDto();
        dto.setAuthData(Map.of("key", "value"));

        mockMvc.perform(put("/requests/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.authData.key").value("value"));
    }

    @Test
    void update_shouldReturn404_whenNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(requestRepo.findById(id)).thenReturn(Optional.empty());

        RequestTemplateUpdateDto dto = new RequestTemplateUpdateDto();
        dto.setName("Update");

        mockMvc.perform(put("/requests/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNotFound());
    }

}
