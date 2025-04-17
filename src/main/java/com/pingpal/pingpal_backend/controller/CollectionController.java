package com.pingpal.pingpal_backend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pingpal.pingpal_backend.model.Collection;
import com.pingpal.pingpal_backend.repository.CollectionRepository;

@RestController
@RequestMapping("/collections")
public class CollectionController {
    
    @Autowired
    private CollectionRepository repository;

    @GetMapping
    public List<Collection> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Collection create(@RequestBody Collection collection) {
        return repository.save(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collection> getOne(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
