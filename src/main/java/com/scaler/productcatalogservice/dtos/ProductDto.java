package com.scaler.productcatalogservice.dtos;

import com.scaler.productcatalogservice.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private CategoryDto category;
}
