package com.assignment.inventory.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long batchId ;

    private String batchCode;
    private Integer mrp;
    private Integer sp;
    private Integer purchasePrice;
    private Integer availableQuantity;
    private LocalDate inwardedOn;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gtin_id")
    private Gtin gtin;

    public Batch(Long batchId, String batchCode, Integer availableQuantity, LocalDate inwardedOn, Gtin gtin) {
        this.batchId = batchId;
        this.batchCode = batchCode;
        this.availableQuantity = availableQuantity;
        this.inwardedOn = inwardedOn;
        this.gtin = gtin;
    }
}
