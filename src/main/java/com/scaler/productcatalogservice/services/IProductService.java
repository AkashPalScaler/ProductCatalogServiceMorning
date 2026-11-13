package com.scaler.productcatalogservice.services;

import com.scaler.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);
    List<Product> getAllProducts();
}
