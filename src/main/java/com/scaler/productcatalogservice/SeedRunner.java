package com.scaler.productcatalogservice;

import com.scaler.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.scaler.productcatalogservice.models.*;

import java.math.BigDecimal;

@Component
public class SeedRunner implements CommandLineRunner {

    private final ProductRepository productRepository;

    public SeedRunner(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Products before: " + productRepository.count());

        Category category = new Category();
        category.setName("beauty");


        Product product = new Product();
        product.setName("Essence Mascara Lash Princess");
        product.setDescription("A popular mascara");
        product.setPrice(new BigDecimal("9.99"));
        product.setCategory(category);

        Product saved = productRepository.save(product);
        System.out.println("Saved with id " + saved.getId());

        Product found = productRepository.findById(saved.getId()).orElseThrow();
        System.out.println("Found: " + found.getName() + " costs " + found.getPrice()
                + " in " + found.getCategory().getName());
    }
}
