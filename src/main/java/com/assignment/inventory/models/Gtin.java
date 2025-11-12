package com.assignment.inventory.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Gtin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(unique = true , nullable = false)
    private String gtin ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "gtin", cascade = CascadeType.ALL)
    private List<Batch> batches;

    public Gtin(Long id, String gtin, Product product) {
        this.id = id;
        this.gtin = gtin;
        this.product = product;
    }

}
