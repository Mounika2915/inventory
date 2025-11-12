package com.assignment.inventory.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId ;
    private String productName ;
    private LocalDate createdOn ;

    public Product(Long productId, String productName, LocalDate createdOn) {
        this.productId = productId;
        this.productName = productName;
        this.createdOn = createdOn;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Gtin> gtins;


}
