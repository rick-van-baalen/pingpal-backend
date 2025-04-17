package com.pingpal.pingpal_backend.repository;

import com.pingpal.pingpal_backend.model.RequestTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequestTemplateRepository extends JpaRepository<RequestTemplate, UUID> {

}