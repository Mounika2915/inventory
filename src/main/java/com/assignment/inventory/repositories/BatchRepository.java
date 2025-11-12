package com.assignment.inventory.repositories;

import com.assignment.inventory.models.Batch;
import com.assignment.inventory.models.Gtin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface BatchRepository extends JpaRepository <Batch, Long >{
    List<Batch> findByGtin_GtinAndAvailableQuantityGreaterThan(String gtin, int qty);

    Optional<Batch> findTopByGtin_GtinAndAvailableQuantityLessThanEqualOrderByInwardedOnDesc(String gtin, int qty);

    List<Batch> findByGtin_GtinOrderByInwardedOnDesc(String gtin);


}
