package com.pingpal.pingpal_backend.model;

import jakarta.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RequestTemplate> requests = new ArrayList<>();

    public Collection() {}
    public Collection(String name) {
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<RequestTemplate> getRequests() { return requests; }
    public void setRequests(List<RequestTemplate> requests) { this.requests = requests; }
}