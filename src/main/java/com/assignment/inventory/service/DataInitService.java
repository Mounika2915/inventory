package com.assignment.inventory.service;

import com.assignment.inventory.models.Batch;
import com.assignment.inventory.models.Gtin;
import com.assignment.inventory.models.Product;
import com.assignment.inventory.repositories.BatchRepository;
import com.assignment.inventory.repositories.GtinRepository;
import com.assignment.inventory.repositories.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataInitService {
    private final ProductRepository productRepository;
    private final GtinRepository gtinRepository;
    private final BatchRepository batchRepository;

    public void createSampleData() {

        if (productRepository.count() > 0) return;


        Product p1 = new Product(null, "Product A", LocalDate.now());
        Product p2 = new Product(null, "Product B", LocalDate.now());
        productRepository.saveAll(List.of(p1, p2));


        Gtin g1 = new Gtin(null, "G1", p1);
        Gtin g2 = new Gtin(null, "G2", p1);
        Gtin g3 = new Gtin(null, "G3", p2);
        gtinRepository.saveAll(List.of(g1, g2, g3));


        List<Batch> batches = List.of(
                new Batch(null, "B1", 100, LocalDate.now().minusDays(3), g1),
                new Batch(null, "B2", -200, LocalDate.now().minusDays(2), g1),
                new Batch(null, "B3", 90, LocalDate.now().minusDays(1), g1),
                new Batch(null, "B4", 0, LocalDate.now(), g1),
                new Batch(null, "B5", 110, LocalDate.now().minusDays(3), g2),
                new Batch(null, "B6", -10, LocalDate.now().minusDays(1), g2),
                new Batch(null, "B7", 220, LocalDate.now(), g3)
        );

        batchRepository.saveAll(batches);
    }

}
