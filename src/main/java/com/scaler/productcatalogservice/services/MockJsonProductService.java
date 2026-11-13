package com.scaler.productcatalogservice.services;

import com.scaler.productcatalogservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mockService")
public class MockJsonProductService implements IProductService {
    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }
}
