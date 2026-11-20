package com.scaler.productcatalogservice.controllers;

import com.scaler.productcatalogservice.dtos.CategoryDto;
import com.scaler.productcatalogservice.dtos.PitchDto;
import com.scaler.productcatalogservice.dtos.ProductDto;
import com.scaler.productcatalogservice.mappers.ProductMapper;
import com.scaler.productcatalogservice.models.Category;
import com.scaler.productcatalogservice.models.Product;
import com.scaler.productcatalogservice.services.IProductService;
import com.scaler.productcatalogservice.services.PitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ProductController {
    private final IProductService productService;
    private final PitchService pitchService;
    private final IProductService supplierService;
    private final ProductMapper productMapper;

    public ProductController(IProductService productService, PitchService pitchService, @Qualifier("dummyJsonProductService") IProductService supplierService, ProductMapper productMapper) {
        this.productService = productService;
        this.pitchService = pitchService;
        this.supplierService = supplierService;
        this.productMapper = productMapper;

    }
    @GetMapping("/products")
    public PagedModel<ProductDto> getProducts(@RequestParam(required = false) BigDecimal minPrice,
                                              @RequestParam(required = false) BigDecimal maxPrice,
                                              Pageable pageable) {
        Page<ProductDto> page = productService.searchProducts(minPrice, maxPrice, pageable)
                .map(product -> productMapper.toDto(product));
        return new PagedModel<>(page);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        if(id < 1){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = productService.getProductById(id);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(productMapper.toDto(product), HttpStatus.OK);
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

//    @PostMapping("/products")
//    public Product createProduct(@RequestBody Product product) {
//        product.setId(12l);
//        return product;
//    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product created = productService.createProduct(productMapper.toEntity(productDto));
        return new ResponseEntity<>(productMapper.toDto(created), HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        Product replaced = productService.replaceProduct(productMapper.toEntity(productDto), id);
        if (replaced == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productMapper.toDto(replaced), HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        if (!productService.deleteProduct(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/products/import")
    public String importProducts() {
        int count = 0;
        for (Product product : supplierService.getAllProducts()) {
            productService.createProduct(product);
            count++;
        }
        return "Imported " + count + " products";
    }

}
// /products/id