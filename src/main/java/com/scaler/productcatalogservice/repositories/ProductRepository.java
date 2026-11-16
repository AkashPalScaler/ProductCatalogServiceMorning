package com.scaler.productcatalogservice.repositories;

import com.scaler.productcatalogservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
// productRepository.save
// .findById()