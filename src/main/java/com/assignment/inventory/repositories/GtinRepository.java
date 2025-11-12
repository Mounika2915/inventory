package com.assignment.inventory.repositories;

import com.assignment.inventory.models.Gtin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GtinRepository extends JpaRepository<Gtin, Long> {
    Optional<Gtin> findByGtin(String gtin);
}
