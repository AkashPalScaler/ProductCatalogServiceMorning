package com.scaler.productcatalogservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    private String name;
    private String description;
    @Column(precision = 19, scale = 2) // DECIMAL(19,2)
    private BigDecimal price;
    private String imageUrl;
    // Product M:1 Category
    @ManyToOne(cascade =  CascadeType.ALL) // Whatever you do on product - also do on category
    private Category category;
}
// Always build bigDecimal from string, do not build from double
// new BigDecimal("9.99")
// Break till 8:30 AM