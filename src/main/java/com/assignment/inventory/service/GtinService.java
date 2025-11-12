package com.assignment.inventory.service;

import com.assignment.inventory.dtos.BatchDto;
import com.assignment.inventory.dtos.GtinResponseDto;
import com.assignment.inventory.models.Batch;
import com.assignment.inventory.models.Gtin;
import com.assignment.inventory.models.Product;
import com.assignment.inventory.repositories.BatchRepository;
import com.assignment.inventory.repositories.GtinRepository;
import com.assignment.inventory.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GtinService {
    private final GtinRepository gtinRepository;
    private final BatchRepository batchRepository;
    private final DataInitService dataInitService;


    public void initData() {
        dataInitService.createSampleData();
    }

    public void createBatch(String gtinCode, BatchDto batchDto) {
        Gtin gtin = gtinRepository.findByGtin(gtinCode)
                .orElseThrow(() -> new RuntimeException("GTIN not found: " + gtinCode));

        Batch batch = batchDto.toEntity(gtin);
        batchRepository.save(batch);
    }

    public GtinResponseDto getBatchesByGtin(String gtin) {
        List<Batch> batches = batchRepository.findByGtin_GtinOrderByInwardedOnDesc(gtin);
        return new GtinResponseDto(gtin, batches.stream().map(BatchDto::fromEntity).toList());
    }


    public List<GtinResponseDto> getFilteredGtins() {
        List<GtinResponseDto> result = new ArrayList<>();

        List<Gtin> allGtins = gtinRepository.findAll();
        for (Gtin gtin : allGtins) {
            List<Batch> positiveBatches =
                    batchRepository.findByGtin_GtinAndAvailableQuantityGreaterThan(gtin.getGtin(), 0);

            Optional<Batch> latestZeroOrNegative =
                    batchRepository.findTopByGtin_GtinAndAvailableQuantityLessThanEqualOrderByInwardedOnDesc(gtin.getGtin(), 0);

            List<BatchDto> batchDtos = new ArrayList<>();
            positiveBatches.forEach(batch -> batchDtos.add(BatchDto.fromEntity(batch)));
            latestZeroOrNegative.ifPresent(batch -> batchDtos.add(BatchDto.fromEntity(batch)));

            if (!batchDtos.isEmpty()) {
                result.add(new GtinResponseDto(gtin.getGtin(), batchDtos));
            }
        }

        return result;
    }
}
