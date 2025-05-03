package com.pingpal.pingpal_backend.controller;

import com.pingpal.pingpal_backend.dto.RequestTemplateCreateDto;
import com.pingpal.pingpal_backend.dto.RequestTemplateUpdateDto;
import com.pingpal.pingpal_backend.model.RequestTemplate;
import com.pingpal.pingpal_backend.repository.RequestTemplateRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/requests")
public class RequestTemplateController {

    @Autowired
    private RequestTemplateRepository requestRepo;

    @GetMapping
    public List<RequestTemplate> getAll() {
        return requestRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RequestTemplateCreateDto dto) {
        RequestTemplate template = new RequestTemplate();
        template.setName(dto.getName());
        template.setMethod(dto.getMethod());
        template.setUrl(dto.getUrl());
        template.setParams(dto.getParams());
        template.setHeaders(dto.getHeaders());
        template.setAuthData(dto.getAuthData());
        template.setBody(dto.getBody());

        RequestTemplate saved = requestRepo.save(template);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestTemplate> getOne(@PathVariable UUID id) {
        return requestRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestTemplate> update(
            @PathVariable UUID id,
            @RequestBody RequestTemplateUpdateDto dto) {
        return requestRepo.findById(id).map(existing -> {
            if (dto.getName() != null) {
                existing.setName(dto.getName());
            }
            if (dto.getMethod() != null) {
                existing.setMethod(dto.getMethod());
            }
            if (dto.getUrl() != null) {
                existing.setUrl(dto.getUrl());
            }
            if (dto.getParams() != null) {
                existing.setParams(dto.getParams());
            }
            if (dto.getHeaders() != null) {
                existing.setHeaders(dto.getHeaders());
            }
            if (dto.getAuthData() != null) {
                existing.setAuthData(dto.getAuthData());
            }
            if (dto.getBody() != null) {
                existing.setBody(dto.getBody());
            }

            return ResponseEntity.ok(requestRepo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (requestRepo.existsById(id)) {
            requestRepo.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not found
        }
    }
    
}