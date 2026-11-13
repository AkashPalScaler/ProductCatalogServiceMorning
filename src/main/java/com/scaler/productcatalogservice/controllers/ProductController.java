package com.scaler.productcatalogservice.controllers;

import com.scaler.productcatalogservice.dtos.PitchDto;
import com.scaler.productcatalogservice.models.Product;
import com.scaler.productcatalogservice.services.IProductService;
import com.scaler.productcatalogservice.services.PitchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final IProductService productService;
    private final PitchService pitchService;
    public ProductController(@Qualifier("dummyJsonProductService") IProductService productService, PitchService pitchService) {
        this.productService = productService;
        this.pitchService = pitchService;

    }
    @GetMapping("/products")
    public List<Product> getProducts() {
        Product product = new Product();
        product.setName("IPhone 17");
        Product product2 = new Product();
        product2.setName("IPhone 18");
        return List.of(product, product2);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        if(id < 1){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = productService.getProductById(id);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/products/{id}/pitch")
    public ResponseEntity<PitchDto> getProductPitchById(@PathVariable("id") Long id) {
        if(id < 1){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = productService.getProductById(id);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        PitchDto pitchDto = new PitchDto();
        pitchDto.setProductName(product.getName());
        pitchDto.setNotification(pitchService.generatePitch(product));
        return new ResponseEntity<>(pitchDto, HttpStatus.OK);
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        product.setId(12l);
        return product;
    }
}
// /products/id