package com.pingpal.pingpal_backend.controller;

import com.pingpal.pingpal_backend.model.RequestTemplate;
import com.pingpal.pingpal_backend.repository.RequestTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public RequestTemplate create(@RequestBody RequestTemplate template) {
        return requestRepo.save(template);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestTemplate> getOne(@PathVariable UUID id) {
        return requestRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestTemplate> update(@PathVariable UUID id, @RequestBody RequestTemplate updatedRequest) {
        return requestRepo.findById(id).map(existing -> {
            existing.setName(updatedRequest.getName());
            existing.setMethod(updatedRequest.getMethod());
            existing.setUrl(updatedRequest.getUrl());
            existing.setParams(updatedRequest.getParams());
            existing.setHeaders(updatedRequest.getHeaders());
            existing.setAuthType(updatedRequest.getAuthType());
            existing.setAuthData(updatedRequest.getAuthData());
            existing.setBody(updatedRequest.getBody());
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