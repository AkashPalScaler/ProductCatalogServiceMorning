package com.scaler.productcatalogservice.services;

import com.scaler.productcatalogservice.models.*;
import com.scaler.productcatalogservice.repositories.CategoryRepository;
import com.scaler.productcatalogservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class StorageProductService implements IProductService {
    @Override
    public Page<Product> searchProducts(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        if (minPrice != null && maxPrice != null) {
            return productRepo.findByPriceBetweenAndState(minPrice, maxPrice, State.ACTIVE, pageable);
        }
        return productRepo.findAllByState(State.ACTIVE, pageable);
    }

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    public StorageProductService(ProductRepository productRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findByIdAndState(id, State.ACTIVE).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }


    @Override
    @Transactional
    public Product createProduct(Product input) {
        input.setId(null);
        input.setCategory(findOrCreateCategory(input.getCategory()));

        Date now = new Date();
        input.setCreatedAt(now);
        input.setLastUpdatedAt(now);
        input.setState(State.ACTIVE);
        return productRepo.save(input);
    }

    private Category findOrCreateCategory(Category category) {
        if (category == null) {
            return null;
        }

        Optional<Category> existing = categoryRepo.findByName(category.getName());
        if (existing.isPresent()) {
            return existing.get();
        }

        Date now = new Date();
        category.setId(null);
        category.setCreatedAt(now);
        category.setLastUpdatedAt(now);
        return category;
    }

    @Override
    @Transactional
    public Product replaceProduct(Product input, Long id) {
        Optional<Product> optionalProduct = productRepo.findByIdAndState(id, State.ACTIVE);
        if (optionalProduct.isEmpty()) {
            return null;
        }

        input.setId(id);
        input.setCategory(findOrCreateCategory(input.getCategory()));
        input.setCreatedAt(optionalProduct.get().getCreatedAt());
        input.setLastUpdatedAt(new Date());
        input.setState(optionalProduct.get().getState());
        return productRepo.save(input);
    }

    @Override
    @Transactional
    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepo.findByIdAndState(id, State.ACTIVE);
        if (optionalProduct.isEmpty()) {
            return false;
        }

        Product product = optionalProduct.get();
        product.setState(State.INACTIVE);
        product.setLastUpdatedAt(new Date());
        productRepo.save(product);
        return true;
    }
}
