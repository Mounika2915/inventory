package com.assignment.inventory.dtos;

import com.assignment.inventory.models.Batch;
import com.assignment.inventory.models.Gtin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchDto {
    private String batchCode;
    private Integer availableQuantity;
    private LocalDate inwardedOn;

    public Batch toEntity(Gtin gtin) {
        Batch batch = new Batch();
        batch.setBatchCode(this.batchCode);
        batch.setAvailableQuantity(this.availableQuantity);
        batch.setInwardedOn(this.inwardedOn);
        batch.setGtin(gtin);
        return batch;
    }

    public static BatchDto fromEntity(Batch batch) {
        return new BatchDto(batch.getBatchCode(), batch.getAvailableQuantity(), batch.getInwardedOn());
    }
}
