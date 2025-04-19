package com.pingpal.pingpal_backend.model;

import jakarta.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class RequestTemplate {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String method;
    private String url;

    @ElementCollection
    @CollectionTable(name = "request_params", joinColumns = @JoinColumn(name = "request_id"))
    @MapKeyColumn(name = "param_key")
    @Column(name = "param_value")
    private Map<String, String> params = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "request_headers", joinColumns = @JoinColumn(name = "request_id"))
    @MapKeyColumn(name = "header_key")
    @Column(name = "header_value")
    private Map<String, String> headers = new HashMap<>();

    // -- Authentication --
    @Transient
    private Map<String, String> authData;

    @JsonIgnore
    @Column(columnDefinition = "TEXT")
    private String authDataJson;

    @PostLoad
    private void loadData() {
        try {
            this.authData = OBJECT_MAPPER.readValue(this.authDataJson, new TypeReference<>() {});
        } catch (Exception e) {
            this.authData = new HashMap<>();
        }

        try {
            this.body = OBJECT_MAPPER.readValue(this.bodyJson, new TypeReference<>() {});
        } catch (Exception e) {
            this.body = new HashMap<>();
        }
    }

    @PrePersist
    @PreUpdate
    private void storeData() {
        try {
            this.authDataJson = OBJECT_MAPPER.writeValueAsString(this.authData);
        } catch (Exception e) {
            this.authDataJson = "{}";
        }

        try {
            this.bodyJson = OBJECT_MAPPER.writeValueAsString(this.body);
        } catch (Exception e) {
            this.bodyJson = "{}";
        }
    }

    // -- Request Body --
    @Transient
    private Map<String, String> body;

    @Column(columnDefinition = "TEXT")
    private String bodyJson;

    // Constructors
    public RequestTemplate() {}

    // Getters & Setters
    public UUID getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Map<String, String> getParams() { return params; }
    public void setParams(Map<String, String> params) { this.params = params; }

    public Map<String, String> getHeaders() { return headers; }
    public void setHeaders(Map<String, String> headers) { this.headers = headers; }

    public Map<String, String> getAuthData() { return authData; }
    public void setAuthData(Map<String, String> authData) { this.authData = authData; }

    public Map<String, String> getBody() { return body; }
    public void setBody(Map<String, String> body) { this.body = body; }
}
