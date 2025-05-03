package com.pingpal.pingpal_backend.dto;

import java.util.HashMap;
import java.util.Map;

public class RequestTemplateUpdateDto {
    
    private String name;
    private String method;
    private String url;
    private Map<String, String> params = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> authData = new HashMap<>();
    private Map<String, String> body = new HashMap<>();

    public RequestTemplateUpdateDto() {}

    public RequestTemplateUpdateDto(String name, String method, String url,
                              Map<String, String> params,
                              Map<String, String> headers,
                              Map<String, String> authData,
                              Map<String, String> body) {
        this.name = name;
        this.method = method;
        this.url = url;
        this.params = params != null ? params : new HashMap<>();
        this.headers = headers != null ? headers : new HashMap<>();
        this.authData = authData != null ? authData : new HashMap<>();
        this.body = body != null ? body : new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params != null ? params : new HashMap<>();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers != null ? headers : new HashMap<>();
    }

    public Map<String, String> getAuthData() {
        return authData;
    }

    public void setAuthData(Map<String, String> authData) {
        this.authData = authData != null ? authData : new HashMap<>();
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body != null ? body : new HashMap<>();
    }

}
