package com.scaler.productcatalogservice.services;

import com.scaler.productcatalogservice.dtos.DummyJsonProductDto;
import com.scaler.productcatalogservice.models.Category;
import com.scaler.productcatalogservice.models.Product;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class DummyJsonProductService implements IProductService{
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
    public List<Product> getAllProducts() {
        return List.of();
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
