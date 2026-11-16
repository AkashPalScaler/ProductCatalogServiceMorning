package com.scaler.productcatalogservice.dtos;

import java.math.BigDecimal;

public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private CategoryDto categoryDto;
}
