package com.assignment.inventory.repositories;

import com.assignment.inventory.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
