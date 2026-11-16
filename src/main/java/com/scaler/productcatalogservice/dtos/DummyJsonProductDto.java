package com.scaler.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class DummyJsonProductDto {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String thumbnail;
    private BigDecimal price;
}
