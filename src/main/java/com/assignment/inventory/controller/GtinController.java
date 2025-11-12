package com.assignment.inventory.controller;

import com.assignment.inventory.dtos.BatchDto;
import com.assignment.inventory.dtos.GtinResponseDto;
import com.assignment.inventory.service.GtinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GtinController {
    private final GtinService gtinService;


    @PostMapping("/data/init")
    public ResponseEntity<String> initData() {
        gtinService.initData();
        return ResponseEntity.ok("Sample data created successfully!");
    }


    @PostMapping("/gtins/{gtin}/batches")
    public ResponseEntity<String> addBatch(@PathVariable String gtin, @RequestBody BatchDto batchDto) {
        gtinService.createBatch(gtin, batchDto);
        return ResponseEntity.ok("Batch created successfully!");
    }



    @GetMapping("/gtins/{gtin}")
    public ResponseEntity<GtinResponseDto> getByGtin(@PathVariable String gtin) {
        return ResponseEntity.ok(gtinService.getBatchesByGtin(gtin));
    }


    @GetMapping("/gtins/filtered")
    public ResponseEntity<List<GtinResponseDto>> getFiltered() {
        return ResponseEntity.ok(gtinService.getFilteredGtins());
    }
}
