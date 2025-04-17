package com.pingpal.pingpal_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pingpal.pingpal_backend.model.Collection;
import java.util.UUID;

public interface CollectionRepository extends JpaRepository<Collection, UUID> {}