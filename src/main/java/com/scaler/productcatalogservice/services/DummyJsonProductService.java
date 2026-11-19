package com.scaler.productcatalogservice.services;

import com.scaler.productcatalogservice.dtos.DummyJsonProductDto;
import com.scaler.productcatalogservice.dtos.DummyJsonProductListDto;
import com.scaler.productcatalogservice.models.Category;
import com.scaler.productcatalogservice.models.Product;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Service
public class DummyJsonProductService implements IProductService{
    @Override
    public Page<Product> searchProducts(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return null;
    }

    private final RestTemplate restTemplate;

    public DummyJsonProductService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }
    @Override
    public Product getProductById(Long id) {
        try{
            ResponseEntity<DummyJsonProductDto> responseEntity =
                    restTemplate.getForEntity("https://dummyjson.com/products/{id}", DummyJsonProductDto.class, id);
            if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                return from(responseEntity.getBody());
            }
            return null;
        } catch (HttpClientErrorException e) {
            System.out.println("DummyJsonProductService getProductById Exception: " + e.getMessage());
            return null;
        }

    }

    @Override
    public Product createProduct(Product input) {
        return null;
    }

    @Override
    public Product replaceProduct(Product input, Long id) {
        return null;
    }

    @Override
    public boolean deleteProduct(Long id) {
        return false;
    }

    @Override
    public List<Product> getAllProducts() {
        ResponseEntity<DummyJsonProductListDto> response = restTemplate.getForEntity(
                "https://dummyjson.com/products?limit=0", DummyJsonProductListDto.class);

        List<Product> products = new ArrayList<>();
        for (DummyJsonProductDto dto : response.getBody().getProducts()) {
            products.add(from(dto));
        }
        return products;
    }

    public Product from(DummyJsonProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getThumbnail());

        Category category = new Category();
        category.setName(dto.getCategory());
        product.setCategory(category);
        return product;
    }
}
